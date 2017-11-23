package com.fgl.cwp.reporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.io.UTF8Reader;
import org.faceless.pdf2.PDF;
import org.faceless.report.ReportParser;
import org.xml.sax.InputSource;
import com.fgl.cwp.common.SystemProperty;
import com.fgl.cwp.model.Report;
import com.fgl.cwp.reporter.controller.ReporterController;
import com.fgl.cwp.reporter.message.IUniversalMessageListener;
import com.fgl.cwp.reporter.resource.ReporterConstants;
import com.fgl.cwp.model.User;


/**
 * @author jmbarga
 */
public class PdfReporter extends AbstractReporter {
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(PdfReporter.class);

	public PdfReporter(Report report, User user, Reporter reporter,
			String task_key, IUniversalMessageListener listener)
			throws Exception {
		super(report, user, reporter, task_key, listener);
		ReporterController.getInstance();
	}

	public void doReport() {
		generatePdf(getReportView());
	}

	/** For security reasons */
	private void generatePdf(Report report) {
		final int DEFAULT_CONNECTION_TIMEOUT = 20;
		String timeStamp = ReporterConstants.DATE_FORMATTER.format(Calendar
				.getInstance().getTime());

		PDF pdf = null;
		InputStream connInputStream = null;
		UTF8Reader utf8Reader = null;
		HttpURLConnection conn = null;
		StringReader stringReader = null;
		FileOutputStream fos = null;
		Integer timeout = null;

		try {
			// Read the xml template from the given URL
			URL url = new URL(report.getExecuteReportAtUrl());
			conn = (HttpURLConnection) url.openConnection();
			timeout = SystemProperty.toIntegerValue(ReporterConstants.KEY_CONNECTION_TIMEOUT);
			if (timeout == null) {
				timeout = new Integer(DEFAULT_CONNECTION_TIMEOUT);
			}
			// maybe conn.setConnectionTimeout
			conn.setConnectTimeout(timeout.intValue() * 60 * 1000);
			conn.setReadTimeout(timeout.intValue() * 60 * 1000);
			int code = conn.getResponseCode();

			if (code == HttpURLConnection.HTTP_OK) {
				// Read the response
				connInputStream = conn.getInputStream();
				utf8Reader = new UTF8Reader(connInputStream);

				StringBuffer response = new StringBuffer();
				int c = utf8Reader.read();
				while (c != -1) {
					response.append((char) c);
					c = utf8Reader.read();
				}

				stringReader = new StringReader(response.toString());
				InputSource source = new InputSource(stringReader);
				response = null;
				// write to file system
				pdf = ReportParser.getInstance().parse(source);// move the
																// ReportParser.getInstance()
																// in the
																// ReporterService()
				File pdfFile = getFile(report, timeStamp);

				// make sure the directory exists
				File directory = new File(pdfFile.getParent());
				log.debug("Checking if dir: " + directory.getName()	+ " exists");
				if (!directory.exists()) {
					directory.mkdirs();
				}

				fos = new FileOutputStream(pdfFile);
				pdf.render(fos);
				// Update the report status to processed
				report.setStatus(Report.STATUS_PROCESSED);
				report.setGeneratedPdfUrl(getUrl(report, timeStamp));
				saveReport(report);
				log.info("Report: " + report.getName()	+ " generated successfully");

				emailReport(report, pdfFile);
			} else
				log.error(" Can't connect to "	+ report.getExecuteReportAtUrl());

		} catch (SocketTimeoutException ste) {
			log.error("SocketTimeoutException : " + ste);
			report.setStatus(Report.STATUS_ERROR);
			saveReport(report);
		} catch (SocketException se) {
			log.error("Failed to receive response from server (waited "
					+ timeout.intValue() + " minutes)");
			report.setStatus(Report.STATUS_ERROR);
			saveReport(report);
		} catch (IOException ioe) {
			try {
				int respCode = ((HttpURLConnection) conn).getResponseCode();
				InputStream es = ((HttpURLConnection) conn).getErrorStream();
				log.debug("Failure reason <==> The response code is : "
						+ respCode);
				es.close();
				report.setStatus(Report.STATUS_ERROR);
				saveReport(report);
			} catch (IOException e) {
				log.error("IOException : " + e);
			}

		} catch (Throwable t) {
			log.error("Failed to generate report " + report.getName(), t);
		} finally {
			// clean all resources
			if (stringReader != null) {
				try {
					stringReader.close();
				} catch (Throwable t) {
				}
			}
			if (utf8Reader != null) {
				try {
					utf8Reader.close();
				} catch (Throwable t) {
				}
			}
			if (connInputStream != null) {
				try {
					connInputStream.close();
				} catch (Throwable t) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Throwable t) {
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	protected String getFilename(Report report, String timeStamp) {
		final String PDF_EXT = ".pdf";
		StringBuffer filename = new StringBuffer();
		filename.append(report.getName());
		filename.append("-");
		filename.append(timeStamp);
		filename.append(PDF_EXT);
		return filename.toString();
	}

	protected String getUrl(Report report, String timeStamp) {
		final String SLASH = "/";

		StringBuffer pdfUrl = new StringBuffer();
		pdfUrl.append(SystemProperty.toStringValue(ReporterConstants.KEY_PDF_SERVER_PROTOCOL));
		pdfUrl.append("://");
		pdfUrl.append(SystemProperty.toStringValue(ReporterConstants.KEY_PDF_SERVER));

		String port = SystemProperty.toStringValue(ReporterConstants.KEY_PDF_SERVER_PORT);
		if (StringUtils.isNotEmpty(port)) {
			pdfUrl.append(":");
			pdfUrl.append(port);
		}

		String virtualDir = SystemProperty.toStringValue(ReporterConstants.KEY_PDF_VIRTUAL_DIRECTORY);
		if (StringUtils.isNotEmpty(virtualDir)) {
			pdfUrl.append(SLASH);
			pdfUrl.append(virtualDir);
		}

		String server = report.getServer();
		if (StringUtils.isNotEmpty(server)) {
			pdfUrl.append(SLASH);
			pdfUrl.append(server);
		}
		pdfUrl.append(SLASH);
		pdfUrl.append(getFilename(report, timeStamp));
		return pdfUrl.toString();
	}

}
