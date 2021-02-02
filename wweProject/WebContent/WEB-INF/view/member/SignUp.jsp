<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<link href="../resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../resources/css/sb-admin-2.css" rel="stylesheet">
</head>

<body class="bg-gradient-primary">
 <%!
     public int getAuthCode(){
          int random = 0;
          random = (int)Math.floor((Math.random()*(99999-10000+1)))+10000;
          return random;
     }
 %>

    <div class="container">

        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                    <div class="col-lg-7">
                        <div class="p-5">
                            <div class="text-center">
                                <h1 class="h4 text-gray-900 mb-4">♣Create an Account!♣</h1>
                            </div>
                            <form class="user" action = "${context}/member/signup" method = "post">
                                <div class="form-group">
                                        <input type="text" class="form-control form-control-user" 
                                        id="userID" name = "userID" placeholder="ID">
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                        <input type="password" class="form-control form-control-user"
                                            id="userPW" name = "userPW" placeholder="Password" onchange = "check_pw()">
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="password" class="form-control form-control-user"
                                            id="userPW2" name = "userPW2" placeholder="Repeat Password"onchange = "check_pw()">
                                    </div>
                                    
                                </div>
                                <span class="form-control-user" id = "checkPW"></span>
                                <br>
                                <div class="form-group">
                                    <input type="email" class="form-control form-control-user" 
                                    id="userEmail" name = "userEmail" placeholder="Email Address">
                                </div>
                                
                                <!-- 인증번호 입력 -->
                                <div class="form-group" id="mailAuth">
                                    <input type="email" class="form-control form-control-user" 
                                    id="userEmailAuth" name = "userEmailAuth" placeholder="인증번호를 입력하세요..">
                                </div>
                                
                                <a onclick = "sendForSignUp();" class="btn btn-primary btn-user btn-block" id="mailCheckBefore">
                                    인증메일 전송
                                    
         							<!-- 인증코드 생성 -->                           
                                    <input type = "hidden" readonly = "readonly" name = "authCodeCheck"
                                	id = "authCodeCheck" value = "<%=getAuthCode()%>"/>
                                	
                                </a>
                                
                                <!-- 인증코드 확인 후 입력창 -->
                                <a onclick = "mailAuthSuccess(); " class="btn btn-primary btn-user btn-block" id="mailCheckAfter" onchange = "mailAuthSuccess()">
                                    인증번호 확인
                                </a>
                                <span class="form-control-user" id = "checkEmail"></span>

                                <div class="form-group">
                                    <input type="text" class="form-control form-control-user" 
                                    id="userName" name = "userName" placeholder="Name">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control form-control-user" 
                                    id="userAdd" name = "userAdd" placeholder="Address">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control form-control-user" 
                                    id="userTell" name = "userTell" placeholder="Tell">
                                </div>
                                <div class="form-group">
                                    <input type="date" class="form-control form-control-user" 
                                    id="userBirth" name = "userBirth" placeholder="Birth">
                                </div>
                                <a onclick = "signup()" class="btn btn-primary btn-user btn-block">
                                    Register Account
                                </a>
                                <hr>
                                <a href="index.html" class="btn btn-google btn-user btn-block">
                                    <i class="fab fa-google fa-fw"></i> Register with Google
                                </a>
                                <a href="index.html" class="btn btn-facebook btn-user btn-block">
                                    <i class="fab fa-facebook-f fa-fw"></i> Register with Facebook
                                </a>
                            </form>
                            <hr>
                            <div class="text-center">
                                <a class="small" href="${context}/member/signin">Already have an account? Login!</a>
                            </div>
                            <div class="text-center">
                                <a class="small" href="${context}/member/find" onclick = "setDivFirstSet()">Forgot ID / Password?</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="../resources/vendor/jquery/jquery.min.js"></script>
    <script src="../resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="../resources/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="../resources/js/sb-admin-2.min.js"></script>
    <script src="../resources/js/model/signController.js"></script>

</body>

</html>