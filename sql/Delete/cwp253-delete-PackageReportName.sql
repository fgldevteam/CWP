-- Below Query is to verify that record with report name as "PackagePriceChange" exist
SELECT * FROM REPORTER WHERE REPORT_NAME='PackagePriceChange';

-- Below Query is to delete Report record 
DELETE FROM REPORTER WHERE REPORT_NAME='PackagePriceChange';

-- Below Query  is to verify that record with report_name PackagePriceChange deleted, should return 0 records
SELECT * FROM REPORTER WHERE REPORT_NAME='PackagePriceChange';

