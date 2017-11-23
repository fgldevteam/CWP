<script type="text/javascript">
Calendar.setup({
    inputField  :   "<%=request.getParameter("inputField")%>",
    ifFormat    :   "%m/%d/%Y",
    button      :   "<%=request.getParameter("button")%>",
    singleClick :   true,
    firstDay    : 0,
    weekNumbers : false
});
</script>
