<template>
  <h1>个人管理中心</h1>
    <el-container>
      <div>
        <el-upload
          class="avatar-uploader"
          :show-file-list="false"
          accept=".jpg,.png"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <h6>个人头像</h6>
        <i class="el-icon-warning"></i>
        <h6>点击头像可更改</h6><br>
        <img alt="volunteer" class="logo" src="@/assets/暂无相关订单.svg" />
        <el-input v-model="input1" placeholder="用户名" :disabled="!isEditing" :readOnly="!isEditing" @blur="handleBlur(1)">
    <i class="el-icon-edit el-icon-edit-outline" slot="suffix" v-show="!isEditing" @click="handleEdit(1)"></i>
  </el-input><br><br>
    <el-input placeholder="电话号码" v-model="input" ></el-input><br><br>
    <el-input placeholder="密码" v-model="input" show-password></el-input><br>
    <el-checkbox v-model="maleChecked" @change="handleCheckboxChange('male')">男</el-checkbox>
          <el-checkbox v-model="femaleChecked" @change="handleCheckboxChange('female')">女</el-checkbox>
          <div :class="{'checked-box': maleChecked}">
            <i class="el-icon-check"></i>
          </div>
          <div :class="{'checked-box': femaleChecked}">
            <i class="el-icon-check"></i>
          </div>
      </div>
      <el-main class="main-container">
  <el-col :span="6">
    <h4>个人活动中心</h4>
    
      <el-card class="box-card">
  <div class="activity-list"></div>
    <el-main1 class="main1">
      <el-input-number v-model="num" @change="handleChange" :min="0" :max="10" label="描述文字" class="activity-counter"></el-input-number>
<template v-solt:append>
  <span>{{ totalActivities }}</span>
</template>
    </el-main1>
</el-card>
    
    
  </el-col>
</el-main>
 <el-card>
  <div class="background-image">
    <span style="float: left" shadow="hover"><b>技能与兴趣</b></span><br><br>
    <el-checkbox-group v-model="checkedSkills" style="height:500px;overflow-y:scroll;">
  <el-col :span="6" v-for="skill in skills" :key="skill.value">
    <el-checkbox
      :label="skill.value"
      :name="skill.value"
      :class="{ 'checked': skill.checked }"
      @change="handleCheckboxChange(skill.value)"
    >
      {{ skill.name }}
      <i class="el-icon-check"></i>
    </el-checkbox>
  </el-col>
  <el-col :span="6">
    <el-input v-model="newSkill" placeholder="请输入新的技能或兴趣" class="new-skill-input"></el-input>
  </el-col>
  <el-col :span="6">
    <el-button type="primary" @click="handleAddNewSkill" class="new-skill-button">添加</el-button>
  </el-col>
</el-checkbox-group>
  </div>
</el-card>

    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
      skills: [
      { name: '跑步', value: 'run', checked: false },
      { name: '游泳', value: 'swim', checked: false },
      { name: '看书', value: 'read', checked: false },
      { name: '音乐', value: 'music', checked: false },
      { name: '美术', value: 'art', checked: false },
      
    ],
    newSkill:'',
    num:0,
    checkedSkills:[],
        imageUrl: '',
        maleChecked: false,
        femaleChecked: false,
        input1:'',
        input:'',
        runClicked: false,
        swimClicked: false,
        readClicked: false,
        isEditing: true,
     originalValue: '',
     activities:[],
      };
    },
    methods: {
    handleAddNewSkill(){
      this.skills.push({ name: this.newSkill, value: this.newSkill.toLowerCase(), checked: false });
      this.newSkill = '';
    },
    computed: {
  totalActivities() {
    return this.activities,lengh;
  },
},
    handleChange(value) {
        console.log(value);
      },
        handleBlur(index) {
    if (this.input1 !== this.originalValue && !this.isEditing) {
      this.$confirm('您有未保存的修改，是否保存？', '提示', {
        confirmButtonText: '保存',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(() => {
        this.input1 = this.originalValue;
        this.$message({
          type: 'success',
          message: '保存成功!'
        });
      })
      .catch(() => {
        this.$message({
          type: 'info',
          message: '已取消保存'
        });
      });
    }
  },
      handleButtonClick(activity) {
        if (activity === 'run') {
          this.runClicked = !this.runClicked;
        } else if (activity === 'swim') {
          this.swimClicked = !this.swimClicked;
        } else if (activity === 'read') {
          this.readClicked = !this.readClicked;
        }
      },
      handleEdit(index) {
    if (index === 1) {
      this.originalValue = this.input1;
    }
    this.isEditing = !this.isEditing;
  },
  handleCheckboxChange(value) {
  const skill = this.skills.find(item => item.value === value);
  if (skill) {
    this.$set(this.checkedSkills, this.checkedSkills.indexOf(skill.value), !skill.checked);
  }
},
  addActivity(activity){
    if(activity.checked){
      this.activities.push(activity);
    }else{
      const index=this.activities.findIndex((item)=>item.value===activity.value);
   if(index!==-1){
    this.acticities.spilve(index,1);
   }
    }
  },
      handleAvatarSuccess(res, file) {
        this.$set(this, 'imageUrl', URL.createObjectURL(file.raw));
      },
      beforeAvatarUpload(file) {
        // 在头像上传之前需要做的判断，如判断文件格式
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;
      },
    },
  };
  
</script>
<style>
.el-main {
  background-color: #dbeeec;
  color: #333;
  text-align: center;
  line-height: 200px;
  padding:0;
  height:100%;
}
.el-col {
padding:20px;
}

.text {
  font-size: 100px;
}

.clicked {
  background-color: #c1183a !important;
}
.cord{
  border: 1px dashed #c71414;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.box-card {
  position: relative;
}
.background-image {
  background-size: cover;
  background-position: center;
  height: 100%;
  width: 100%;
}
.new-skill-input {
  width: 200px;
  margin-right: 10px;
}

.new-skill-button {
  margin-top: 5px;
}

.checked-box {
  width: 20px;
  height: 20px;
  border: 1px solid #d7e90d;
  display: flex;
  justify-content: center;
  align-items: center;
}
  .avatar-uploader .el-upload {
    border: 1px dashed #c71414;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #1b61c3;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #0a50b2;
  }
  .bg-purple {
    background: #1470da;
  }
  .bg-purple-light {
    background: #1449c6;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #0f4bc4;
  }
  .main-container {
  position: relative;
  height: 100%;
}

.activity-counter {
  position: absolute;
  bottom: 20px;
  right: 20px;
}
.checked {
  color: #409EFF;
}

.checked .el-icon-check {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: #409EFF;
}


</style>
