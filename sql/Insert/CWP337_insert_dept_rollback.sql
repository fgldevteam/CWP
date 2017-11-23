
/*

CWP-337 30-Nov-2009 Created

Note: Run on CWP database for rollback for CWP-337

*/

DELETE FROM sum_total_dept WHERE dept_abbrv IN ( 'FTW', 'CLO', 'SMA', 'HXA' )
GO
