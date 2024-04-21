<template>
    <h1>个人资料管理</h1>
    <el-container>
        <el-aside class="small-box">个人图片</el-aside>
    </el-container>
    <el-table-column label="修改数据" width="200px">
        <template slot-scop="{ }"></template>
    </el-table-column>
</template>
<script>

</script>
<style>  
    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        width: 200px; /* 调整宽度为150px，使方框变小 */
        line-height: 200px; /* 调整行高为150px，使文本垂直居中 */
    }

    .small-box {
        width: 15=200px; /* 调整宽度为150px，覆盖全局样式 */
        line-height: 200px; /* 调整行高为150px，覆盖全局样式 */
    }
</style>
