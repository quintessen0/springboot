<template>
  <div class="attendance-container">
    <h2>考勤管理</h2>
    
    <!-- 搜索条件 -->
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item label="员工姓名">
        <el-input v-model="searchForm.empName" placeholder="请输入员工姓名" style="width: 180px;" />
      </el-form-item>
      <el-form-item label="考勤日期">
        <el-date-picker
          v-model="searchForm.attendanceDate"
          type="date"
          placeholder="选择考勤日期"
          style="width: 180px;"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="考勤状态">
        <el-select v-model="searchForm.status" placeholder="选择考勤状态" style="width: 150px;">
          <el-option label="全部" value="" />
          <el-option label="正常" value="1" />
          <el-option label="迟到" value="2" />
          <el-option label="早退" value="3" />
          <el-option label="缺勤" value="4" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="showAddDialog">新增记录</el-button>
      </el-form-item>
    </el-form>

    <!-- 考勤表格 -->
    <el-table :data="attendances" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="attId" label="考勤ID" width="80" />
      <el-table-column prop="employee.empName" label="员工姓名" />
      <el-table-column prop="createTime" label="考勤日期" width="120" :formatter="formatDate" />
      <el-table-column prop="checkIn" label="签到时间" width="150" :formatter="formatTime" />
      <el-table-column prop="checkOut" label="签退时间" width="150" :formatter="formatTime" />
      <el-table-column prop="status" label="考勤状态" width="100" :formatter="formatStatus">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">
            {{ getStatusName(row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" />
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="showEditDialog(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.attId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination" style="margin-top: 20px; text-align: center;">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增考勤记录' : '编辑考勤记录'"
      width="600px"
    >
      <el-form
        ref="attendanceFormRef"
        :model="attendanceForm"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="员工" prop="empId">
          <el-select v-model="attendanceForm.empId" placeholder="请选择员工" style="width: 100%;">
            <el-option v-for="emp in employees" :key="emp.empId" :label="emp.empName" :value="emp.empId" />
          </el-select>
        </el-form-item>
        <el-form-item label="考勤日期" prop="createTime">
          <el-date-picker
            v-model="attendanceForm.createTime"
            type="date"
            placeholder="请选择考勤日期"
            style="width: 100%;"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="签到时间" prop="checkIn">
          <el-time-picker
            v-model="attendanceForm.checkIn"
            placeholder="请选择签到时间"
            style="width: 100%;"
            value-format="HH:mm:ss"
            format="HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="签退时间" prop="checkOut">
          <el-time-picker
            v-model="attendanceForm.checkOut"
            placeholder="请选择签退时间"
            style="width: 100%;"
            value-format="HH:mm:ss"
            format="HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="考勤状态" prop="status">
          <el-radio-group v-model="attendanceForm.status">
            <el-radio label="1">正常</el-radio>
            <el-radio label="2">迟到</el-radio>
            <el-radio label="3">早退</el-radio>
            <el-radio label="4">缺勤</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="attendanceForm.remark" placeholder="请输入备注" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAttendances, addAttendance, updateAttendance, deleteAttendance } from '../api/attendance'
import { getEmployees } from '../api/employee'

export default {
  name: 'Attendance',
  setup() {
    // 数据
    const attendances = ref([])
    const employees = ref([])
    const dialogVisible = ref(false)
    const dialogType = ref('add')
    const attendanceFormRef = ref(null)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    // 搜索表单
    const searchForm = reactive({
      empName: '',
      attendanceDate: '',
      status: ''
    })

    // 表单数据
    const attendanceForm = reactive({
      attId: '',
      empId: '',
      createTime: '',
      checkIn: '',
      checkOut: '',
      status: '1',
      remark: ''
    })

    // 表单验证规则
    const rules = {
      empId: [
        { required: true, message: '请选择员工', trigger: 'blur' }
      ],
      createTime: [
        { required: true, message: '请选择考勤日期', trigger: 'blur' }
      ],
      checkIn: [
        { required: true, message: '请选择签到时间', trigger: 'blur' }
      ],
      checkOut: [
        { required: true, message: '请选择签退时间', trigger: 'blur' }
      ],
      status: [
        { required: true, message: '请选择考勤状态', trigger: 'blur' }
      ],
      remark: [
        { max: 200, message: '备注长度不能超过200', trigger: 'blur' }
      ]
    }

    // 格式化日期
    const formatDate = (row, column) => {
      const date = row[column.property]
      if (date) {
        return new Date(date).toLocaleDateString()
      }
      return ''
    }

    // 格式化时间 (简化显示格式)
    const formatTime = (row, column) => {
      const date = row[column.property]
      if (date) {
        // 只显示小时和分钟，不显示秒
        const d = new Date(date)
        return `${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}`
      }
      return ''
    }

    // 格式化状态
    const formatStatus = (row, column) => {
      return getStatusName(row.status)
    }

    // 获取状态名称
    const getStatusName = (status) => {
      switch (status) {
        case 1: return '正常'
        case 2: return '迟到'
        case 3: return '早退'
        case 4: return '缺勤'
        default: return '未知'
      }
    }

    // 获取状态类型
    const getStatusType = (status) => {
      switch (status) {
        case 1: return 'success'
        case 2: return 'warning'
        case 3: return 'warning'
        case 4: return 'danger'
        default: return 'info'
      }
    }

    // 获取员工列表
    const loadEmployees = async () => {
      try {
        const response = await getEmployees()
        if (response.code === 200) {
          employees.value = response.data
        }
      } catch (error) {
        ElMessage.error('获取员工列表失败')
      }
    }

    // 获取考勤记录列表
    const loadAttendances = async () => {
  try {
    const response = await getAttendances({
      page: currentPage.value,
      size: pageSize.value
    })
    if (response.code === 200) {
      attendances.value = response.data.data
      total.value = response.data.total
    }
  } catch (error) {
    ElMessage.error('获取考勤记录失败')
  }
}

    // 搜索考勤记录
    const handleSearch = async () => {
      try {
        const response = await getAttendances({
          ...searchForm,
          page: currentPage.value,
          size: pageSize.value
        });
        if (response.code === 200) {
          attendances.value = response.data.data;
          total.value = response.data.total;
        }
      } catch (error) {
        ElMessage.error('搜索考勤记录失败');
      }
    }

    // 重置搜索
    const resetSearch = () => {
      Object.assign(searchForm, { empName: '', attendanceDate: '', status: '' })
      loadAttendances()
    }

    // 显示新增对话框
    const showAddDialog = () => {
      dialogType.value = 'add'
      Object.assign(attendanceForm, {
        attId: '', empId: '', createTime: '', checkIn: '', checkOut: '', status: '1', remark: ''
      })
      dialogVisible.value = true
    }

    // 显示编辑对话框
    const showEditDialog = (row) => {
      dialogType.value = 'edit'
      // 深拷贝row对象，避免直接修改原数据
      const editData = { ...row }
      // 确保status字段是字符串类型，与单选框的值匹配
      editData.status = String(editData.status)
      // 处理时间字段格式，将Date对象转换为时间字符串
      if (editData.checkIn instanceof Date) {
        const hours = editData.checkIn.getHours().toString().padStart(2, '0')
        const minutes = editData.checkIn.getMinutes().toString().padStart(2, '0')
        const seconds = editData.checkIn.getSeconds().toString().padStart(2, '0')
        editData.checkIn = `${hours}:${minutes}:${seconds}`
      }
      if (editData.checkOut instanceof Date) {
        const hours = editData.checkOut.getHours().toString().padStart(2, '0')
        const minutes = editData.checkOut.getMinutes().toString().padStart(2, '0')
        const seconds = editData.checkOut.getSeconds().toString().padStart(2, '0')
        editData.checkOut = `${hours}:${minutes}:${seconds}`
      }
      Object.assign(attendanceForm, editData)
      dialogVisible.value = true
    }

    // 提交表单
    const handleSubmit = async () => {
      if (!attendanceFormRef.value) return
      
      await attendanceFormRef.value.validate(async (valid) => {
        if (valid) {
          try {
            let response
            if (dialogType.value === 'add') {
              response = await addAttendance(attendanceForm)
            } else {
              response = await updateAttendance(attendanceForm)
            }
            
            if (response.code === 200) {
              ElMessage.success(response.message)
              dialogVisible.value = false
              loadAttendances()
            } else {
              ElMessage.error(response.message)
            }
          } catch (error) {
            ElMessage.error('操作失败，请稍后重试')
          }
        }
      })
    }

    // 删除考勤记录
    const handleDelete = async (attId) => {
      try {
        await ElMessageBox.confirm('确定要删除该考勤记录吗？', '确认删除', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        const response = await deleteAttendance(attId)
        if (response.code === 200) {
          ElMessage.success(response.message)
          loadAttendances()
        } else {
          ElMessage.error(response.message)
        }
      } catch (error) {
        // 用户取消删除
      }
    }

    // 分页处理
    const handleSizeChange = (size) => {
      pageSize.value = size
      loadAttendances()
    }

    const handleCurrentChange = (current) => {
      currentPage.value = current
      loadAttendances()
    }

    // 初始加载
    onMounted(() => {
      loadEmployees()
      loadAttendances()
    })

    return {
      attendances,
      employees,
      searchForm,
      dialogVisible,
      dialogType,
      attendanceFormRef,
      attendanceForm,
      rules,
      currentPage,
      pageSize,
      total,
      formatDate,
      formatStatus,
      getStatusType,
      getStatusName,
      handleSearch,
      resetSearch,
      showAddDialog,
      showEditDialog,
      handleSubmit,
      handleDelete,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.attendance-container {
  padding: 20px;
}

.search-form {
  background-color: #f5f7fa;
  padding: 20px;
  border-radius: 4px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>