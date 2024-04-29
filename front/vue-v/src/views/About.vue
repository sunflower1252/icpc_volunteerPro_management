<template>
    <div>
      <el-input v-model="searchText" placeholder="请输入关键内容" size="medium" style="margin-bottom: 20px; width: 300px;"></el-input>
      <el-select v-model="dateValue" clearable placeholder="全部时间" size="medium" style="margin: 0 30px 20px 0" @change="timeSearch()">
        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-date-picker v-model="valuetime" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" size="medium" @change="submitDateTime()" style="margin-right:30px">
      </el-date-picker>
      <el-button type="primary" @click="filterData">筛选</el-button>
      <el-table :data="filteredTableData" border style="width: 100%">
        <el-table-column fixed prop="date" label="日期" width="150">
        </el-table-column>
        <el-table-column fixed prop="address" label="活动" width="300"></el-table-column>
        <el-table-column fixed prop="time" label="时间" width="150"></el-table-column>
        <el-table-column fixed prop="where" label="地点" width="300"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="primary" @click="signupActivity(scope.row)">报名</el-button>
            <el-button type="text" @click="getActivityDetails(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        searchText: '',
        tableData: [],
        dateValue: "1", // 时间选择状态
        valuetime: [],
        options: [
          { value: '1', label: '全部时间' },
          { value: '2', label: '最近一个月' },
          { value: '3', label: '最近半年' },
          { value: '4', label: '最近一年' }
        ],
        queryInfo: {
          current: 1,
          size: 9,
          status: ['NOT_START', 'ANSWER_IN_PROGRESS', 'DATA_UNDER_REVIEW', 'REPORT'],
          releaseDateFloor: '', // 开始时间
          releaseDateCeil: '' // 结束时间
        }
      };
    },
    computed: {
      filteredTableData() {
        return this.tableData.filter(activity => {
          return activity.address.toLowerCase().includes(this.searchText.toLowerCase())
            || activity.where.toLowerCase().includes(this.searchText.toLowerCase());
        });
      }
    },
    methods: {
      filterData() {
        if (this.dateValue === '1' && (!this.valuetime || this.valuetime.length === 0)) {
          return;
        }
        if (this.dateValue === '1' || (this.valuetime && this.valuetime.length === 2)) {
          this.submitDateTime();
        }
        this.searchText = '';
      },
      timeSearch() {
        // ...（您的时间搜索逻辑）
      },
      submitDateTime() {
        // ...（您的提交时间逻辑）
      },
      async signupActivity(activity) {
        // ...（您的报名活动逻辑）
      },
      async getActivityDetails(activity) {
        // ...（您的获取活动详情逻辑）
      },
      async getScreenList(queryInfo) {
        // ...（您的获取筛选数据逻辑）
      },
      timeFormat(date) {
        // ...（您的时间格式化函数）
      }
    }
  };
  </script>