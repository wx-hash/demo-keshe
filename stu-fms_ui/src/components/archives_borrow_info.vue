<template>
  <div>
    <!-- 搜索框 -->
    <el-form :inline="true" style="display: inline;float: right;" class="demo-form-inline;">
      <el-form-item>
        <el-input placeholder="请输入借阅者...." v-model="searchData" :inline="true">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>
      </el-form-item>
    </el-form>

    <!-- 借阅记录表格 -->
    <el-table :data="tableData" border style="width: 100%" v-loading="tableLoading">
      <el-table-column prop="archiveName" label="档案名称" width="200"></el-table-column>
      <el-table-column prop="stuName" label="被借阅学生" width="150"></el-table-column>
      <el-table-column prop="username" label="借阅者" width="150"></el-table-column>
      <el-table-column prop="borrTime" label="借阅时间" width="150">
        <template slot-scope="scope">
          {{scope.row.borrTime ? scope.row.borrTime.substring(0,10) : ''}}
        </template>
      </el-table-column>
      <el-table-column prop="borrStatus" label="借阅状态" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.borrStatus == 1" type="warning" size="small">借阅中</el-tag>
          <el-tag v-else-if="scope.row.borrStatus == 0" type="success" size="small">已归还</el-tag>
          <el-tag v-else-if="scope.row.borrStatus == -1" type="danger" size="small">超时</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <el-popconfirm title="确定删除该记录吗？" @confirm="delet(scope.row)">
            <el-button slot="reference" type="danger" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination align="right" @current-change="handleCurrentChange"
                   :current-page.sync="currentPage3" :page-size="pageSize" layout="prev, pager, next, jumper"
                   :total="pageNum">
    </el-pagination>

  </div>
</template>

<script>

  import request from '@/utils/request';

  export default {
    name: 'archive-borrow-info',
    data() {
      return {
        //当前页
        currentPage: 1,
        //每页数量
        pageSize: 5,
        //总页数
        pageNum: 100,
        searchData: '',
        // 档案信息表
        tableData: [],
        tableLoading: false,
        //当前页
        currentPage3: 1,
      }
    },
    created() {

      this.getList(this.currentPage, this.pageSize);
    },
    methods: {
      //获取档单信息列表
      //默认第0页，每页5条数据
      getList: function (page, size) {
        this.tableLoading = true;
        request.get('/api/physical/archives/record/search', {
            params: {
              page: page,
              size: size
            }
          }
        ).then(res => {
          //获取表格数据
          var data = res.data.data.records;
          //渲染表格数据
          this.tableData = data;
          //获取总页数
          this.pageNum = res.data.data.total;
          this.tableLoading = false;
        }).catch(error => {
          console.log("api请求失败", error);
          this.tableLoading = false;
        })

      },
      //获取当前页数
      handleCurrentChange(page) {

        this.getList(page, this.pageSize);
        //设置当前页数
        this.currentPage = page;

      },
      //删除
      delet(data) {
        console.log("删除", data);

        request.post("/api/physical/archives/record/delete", data).then(res => {
          if (res.data.status == 0) {
            this.$message({
              message: '删除成功!',
              type: 'success',
              center: true
            })
          } else {
            this.$message({
              message: '删除失败!',
              type: 'warning',
              center: true
            })
          }
        }).catch(error => {

        });
        //重新渲染数据
        this.getList(this.currentPage,this.pageSize);
      },


      //搜索
      search(){
        this.tableLoading = true;
        request.get("/api/physical/archives/record/search",{
          params:{
            page:this.currentPage,
            size:this.pageSize,
            searchValue:this.searchData
          }
        }).then( res =>{
          var data = res.data.data.records;
          this.tableData =data;
          //获取总页数
          this.pageNum = res.data.data.total;
          this.tableLoading = false;
        }).catch( error =>{
          console.log(error);
          this.tableLoading = false;
        })
      }

    },
  }
</script>

<style>
</style>
