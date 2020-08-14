<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<spring:url value="/resources/css/bootstrap-switch-button.min.css" var="sw_button" />
<spring:url value="/resources/css/bootstrap_4_5_0.min.css" var="bs_4_5" />
<spring:url value="/resources/css/index_style.css" var="index_css" />
<spring:url value="/resources/js/jquery_3_2_1min.js" var="jquery_3_2" />
<spring:url value="/resources/js/index_script.js" var="script" />
<spring:url value="/resources/js/bootstrap-switch-button.min.js" var="sw_js" />
<spring:url value="/resources/js/bootstrap.min.js" var="bs_js" />
<link rel="stylesheet" href="${sw_button}">
<link rel="stylesheet" href="${bs_4_5}">
<link rel="stylesheet" href="${index_css}">
<script src="${jquery_3_2}"></script>
<script src="${script}"></script>
<script src="${sw_js}"></script>
<script src="${bs_js}"></script>
<html>
<head>
    <title>Game of Life</title>
</head>
<body>
<div class="container-xl" id="container">
    <canvas id="gameCanvas"></canvas>
    <div id="infoDiv" class="row no-gutters pt-1">
        <div class="col-5">
            &nbsp;Pointer position:&nbsp;<span id="position"></span>
        </div>
        <div class="col-5">
            Generation No:&nbsp;<span id="gen"></span>
        </div>
        <div class="col-2">
            Alive cells:&nbsp;<span id="alive"></span>
        </div>
    </div>
    <div class="row pt-2">
        <div class="col-4">
            <a href="save" class="btn btn-secondary mt-1" id="btn_save" download>SAVE</a>
            <label class="btn btn-secondary mt-1" id="fileLabel">LOAD
                <input type="file" accept=".lif" id="inputFile" style="display: none;">
            </label>
        </div>
        <div class="col-4">
            <button class="btn btn-secondary mt-1" id="btn_start">START</button>
            <button class="btn btn-secondary mt-1" id="btn_stop">STOP</button>
            <button class="btn btn-secondary mt-1" id="btn_step">STEP</button>
            <button class="btn btn-secondary mt-1" id="btn_clear">CLEAR</button>
        </div>
        <div class="col-4">
            <div  class="float-right ml-1 mt-1">
                <input type="checkbox" id="btn_grid" checked data-toggle="switchbutton"
                       data-onlabel=" GRID"
                       data-offlabel="GRID"
                       data-onstyle="secondary"
                       data-offstyle="outline-secondary">
            </div>
            <div id="progressbar" class="float-right mt-1">
                <label id="speedLabel">SPEED
                    <input type="range" id="speedRange" min="10" max="101" class="slider">
                </label>
            </div>
        </div>
    </div>
    <div class="modal fade" id="modalCenter" tabindex="-1" role="dialog"
         aria-labelledby="modalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title">Game of Life</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <h3 class="modal-title text-center">Game over!</h3>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
