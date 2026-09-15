<template>
  <div>
    <el-table :data="tableData" border style="width: 100%" v-loading="tableLoading">
      <el-table-column prop="archiveName" label="档案名称" width="150"></el-table-column>
      <el-table-column prop="stuId" label="学号" width="150"></el-table-column>
      <el-table-column prop="position" label="存放位置" width="150"></el-table-column>
      <el-table-column prop="inTime" label="入库时间" width="150">
        <template slot-scope="scope">
          {{scope.row.inTime ? scope.row.inTime.substring(0,10) : ''}}
        </template>
      </el-table-column>
      <el-table-column prop="borrowTime" label="允许借阅(分钟)" width="130"></el-table-column>
      <el-table-column prop="auditStatusCode" label="档案状态" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.auditStatusCode == 4" type="success" size="small">档案已入库</el-tag>
          <el-tag v-else-if="scope.row.auditStatusCode == 6" type="warning" size="small">档案已出库</el-tag>
          <el-tag v-else-if="scope.row.auditStatusCode == 7" type="danger" size="small">借阅中</el-tag>
          <span v-else>--</span>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination align="right" @current-change="handleCurrentChange"
                   :current-page.sync="currentPage" :page-size="pageSize"
                   layout="prev, pager, next, jumper" :total="total">
    </el-pagination>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'archive-borrow-status',
  data() {
    return {
      tableData: [],
      tableLoading: false,
      currentPage: 1,
      pageSize: 5,
      total: 0
    }
  },
  created() {
    this.getList(this.currentPage, this.pageSize);
  },
  methods: {
    getList(page, size) {
      this.tableLoading = true;
      request.get('/api/physical/archives/search', {
        params: { page: page, size: size }
      }).then(res => {
        this.tableData = res.data.data.records;
        this.total = res.data.data.total;
        this.tableLoading = false;
      }).catch(error => {
        console.log("api请求失败", error);
        this.tableLoading = false;
      });
    },
    handleCurrentChange(page) {
      this.getList(page, this.pageSize);
    }
  }
}
</script>

<style>
</style>
