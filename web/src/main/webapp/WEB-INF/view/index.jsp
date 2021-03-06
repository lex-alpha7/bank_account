<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/header.jsp"/>
<table id="bank_accounts_table">
    <thead>
    </thead>
    <tfoot>
    <tr>
        <th></th><th></th><th></th><th></th>
    </tr>
    </tfoot>
    <tbody></tbody>
</table>

<div style="display: none" >
    <div id="editAccount" title="Account Edit">
        <form id="editAccountForm" name="editAccountForm">
            <div id="valid_error" name="valid_error" class="js_valid_error"></div>
            <table>
                <tr>
                    <td><input type="text" id="idText" name="id" readonly="readonly" hidden="hidden" /></td>
                </tr>
                <tr>
                    <td><label for="bicText">BIC</label></td>
                    <td><input type="text" id="bicText" name="bicText" /></td>
                </tr>
                <tr>
                    <td><label for="ibanText">IBAN</label></td>
                    <td><input type="text" id="ibanText" name="ibanText" /></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<jsp:include page="templates/footer.jsp"/>
