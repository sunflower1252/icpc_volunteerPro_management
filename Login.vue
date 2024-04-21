<template>
 <el-col :span="12">
  <div> <el-form :rules="rules"
     class="tupian" 
     label-position="left"
      label-width="0px" 
      v-loading="loading"> 
  </el-form> 
  </div> 
  </el-col>
   <div class="layout"> 
    <div class="center-container">
  <!--  <img alt="volunter" class="logo" src="@/assets/暂无相关订单.svg" />-->
     <div><h1>Volunter Pro</h1></div>
      </div>
       <el-tabs type="border-card"> 
         <el-tab-pane label="登录">
         <el-form label-position="right" label-width="60px" style="max-width: 460px" class="loginForm" > 
         <el-form-item label="邮箱："> 
            <el-input v-model="Email" /> 
           </el-form-item> <el-form-item label="密码：">
          <el-input type="password" v-model="password" /> 
            </el-form-item> <el-row> 
            <el-checkbox class="checkBox" v-model="isAgree" label="同意用户使用准则" name="type" /> 
            </el-row> 
             <el-button type="primary" class="loginBtn" @click="login" > 登录 </el-button>
             </el-form>
             </el-tab-pane> 
             <el-tab-pane label="注册"> 
              <el-form label-position="right" label-width="100px" style="max-width: 460px" class="loginForm" > 
              <el-form-item label="邮箱："> <el-input v-model="rEmail" />
             </el-form-item> <el-form-item label="密码："> 
             <el-input type="password" v-model="rPassword" /> 
            </el-form-item> <el-form-item label="确认密码：">
             <el-input type="password" v-model="confirmPassword" @blur="confirmFunc" />
           </el-form-item> <el-form-item label="验证码：">
            <el-row> 
            <el-input type="password" v-model="identifyCode" class="inpWidth" />
            <el-button type="primary" @click="getIdentifyCode" plain> 获取验证码 </el-button> 
            </el-row>
                 </el-form-item> 
                                 <el-row> 
            <el-checkbox class="checkBox" v-model="rAgree" label="同意用户使用准则" name="type" /> 
           </el-row> <el-button type="primary" class="loginBtn" @click="register" > 注册 </el-button>
                                 </el-form> 
            </el-tab-pane> 
                                   </el-tabs> 
                                  </div> 
                                   </template>
                                    <script>
                                     import { reactive, toRefs } from "@vue/reactivity";
                                      import { ElMessage } from "element-plus";
                                       export default { setup() { const form = reactive({ Email: "", password: "", isAgree: 0, }); 
                                       const registerForm = reactive({ rEmail: "", rPassword: "", confirmPassword: "", identifyCode: "", rAgree: 0, }); // 方法 // 登录
                                        function login() { console.log(form); } // 注册
                                         function register() { console.log("注册", registerForm); } // 获取验证码
                                          function getIdentifyCode() { console.log("获取验证码"); } 
                                          const confirmFunc = () => { if (registerForm.confirmPassword !== registerForm.rPassword) ElMessage.error("密码与确认密码不一致."); }; 
                                          return { ...toRefs(form), ...toRefs(registerForm), login, register, getIdentifyCode, confirmFunc, };
                                           }, 
                                           }; 
                                           </script>
                                            <style>
                                             .layout 
                                             { display: flex; 
                                             justify-content: center; 
                                             align-items: center; 
                                             position: absolute;
                                              top: 0; 
                                              left: 0; 
                                              width: 100vw;
                                               height: 100vh;
                                                background-image: url('../assets/R-C (1).jpg'); 
                                                background-size: cover;
                                                 background-position: center;
                                                  z-index: 2; /* 设置一个更高的 z-index 值 */ 
                                                  }
             .center-container
             {
            display: flex; 
           justify-content: center; 
            align-items: center;
          flex-direction: column; 
         height: 100vh; /* 让容器铺满整个屏幕高度 */ 
          } 
                                                        .logo { 
                                                          width: 100px;
                                                           height: 100px; } 
                                                           h1 { color: rgb(60, 0, 128); 
                                                          }
                                                           .loginBtn 
                                                           {
                                                             width: 100px;
                                                             } 
                                                             .loginForm 
                                                             { 
                                                              text-align: center;
                                                               background-color: rgba(255, 255, 255, 0.8); /* 透明白色背景，使表单内容更清晰 */
                                                                padding: 20px;
                                                                 border-radius: 10px; } 
                                                                 .checkBox { margin-left: 7px; } 
                                                                 .inpWidth { width: 165px; } 
                                                                  </style>