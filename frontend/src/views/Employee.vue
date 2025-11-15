<template>
  <div class="employee-container">
    <h2>员工管理</h2>
    
    <!-- 搜索条件 -->
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item label="员工姓名">
        <el-input v-model="searchForm.empName" placeholder="请输入员工姓名" style="width: 180px;" />
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input v-model="searchForm.idCard" placeholder="请输入身份证号" style="width: 180px;" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="searchForm.phone" placeholder="请输入手机号" style="width: 180px;" />
      </el-form-item>
      <el-form-item label="所属部门">
        <el-select v-model="searchForm.deptId" placeholder="选择部门" style="width: 150px;">
          <el-option label="全部" value="" />
          <el-option v-for="dept in departments" :key="dept.deptId" :label="dept.deptName" :value="dept.deptId" />
        </el-select>
      </el-form-item>
      <el-form-item label="职位">
        <el-select v-model="searchForm.posId" placeholder="选择职位" style="width: 150px;">
          <el-option label="全部" value="" />
          <el-option v-for="pos in positions" :key="pos.posId" :label="pos.posName" :value="pos.posId" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="showAddDialog">新增员工</el-button>
      </el-form-item>
    </el-form>

    <!-- 员工表格 -->
    <el-table :data="employees" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="empId" label="员工ID" width="80" />
      <el-table-column prop="empName" label="员工姓名" />
      <el-table-column prop="gender" label="性别" />
      <el-table-column prop="idCard" label="身份证号" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="department.deptName" label="所属部门" />
      <el-table-column prop="position.posName" label="职位" />
      <el-table-column prop="entryDate" label="入职日期" width="120" :formatter="formatDate" />
      <el-table-column prop="status" label="状态" width="80" :formatter="formatStatus">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '在职' : '离职' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="showEditDialog(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.empId)">删除</el-button>
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
      :title="dialogType === 'add' ? '新增员工' : '编辑员工'"
      width="700px"
    >
      <el-form
        ref="employeeFormRef"
        :model="employeeForm"
        :rules="rules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="员工姓名" prop="empName">
              <el-input v-model="employeeForm.empName" placeholder="请输入员工姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="employeeForm.gender" placeholder="请选择性别" style="width: 100%;">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="employeeForm.idCard" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="employeeForm.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="employeeForm.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期" prop="entryDate">
              <el-date-picker
                v-model="employeeForm.entryDate"
                type="date"
                placeholder="请选择入职日期"
                style="width: 100%;"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门" prop="deptId">
              <el-select v-model="employeeForm.deptId" placeholder="请选择部门" style="width: 100%;" @change="onDeptChange">
                <el-option v-for="dept in departments" :key="dept.deptId" :label="dept.deptName" :value="dept.deptId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职位" prop="posId">
              <el-select v-model="employeeForm.posId" placeholder="请选择职位" style="width: 100%;">
                <el-option v-for="pos in filteredPositions" :key="pos.posId" :label="pos.posName" :value="pos.posId" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="employeeForm.status">
                <el-radio label="1">在职</el-radio>
                <el-radio label="2">离职</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="地址" prop="address">
          <el-input v-model="employeeForm.address" placeholder="请输入地址" type="textarea" :rows="2" />
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
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getEmployees, queryEmployees, addEmployee, updateEmployee, deleteEmployee } from '../api/employee'
import { getDepartments } from '../api/department'
import { getPositions, getPositionsByDept } from '../api/position'

export default {
  name: 'Employee',
  setup() {
    // 数据
    const employees = ref([])
    const departments = ref([])
    const positions = ref([])
    const dialogVisible = ref(false)
    const dialogType = ref('add')
    const employeeFormRef = ref(null)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    // 搜索表单
    const searchForm = reactive({
      empName: '',
      idCard: '',
      phone: '',
      deptId: '',
      posId: ''
    })

    // 表单数据
    const employeeForm = reactive({
      empId: '',
      empName: '',
      gender: '',
      idCard: '',
      phone: '',
      email: '',
      address: '',
      deptId: '',
      posId: '',
      entryDate: '',
      status: '1'
    })

    // 表单验证规则
    const rules = {
      empName: [
        { required: true, message: '请输入员工姓名', trigger: 'blur' },
        { min: 1, max: 50, message: '员工姓名长度在1-50之间', trigger: 'blur' }
      ],
      idCard: [
        { required: true, message: '请输入身份证号', trigger: 'blur' },
        { pattern: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, 
          message: '身份证号格式不正确', trigger: 'blur' }
      ],
      phone: [
        { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
      ],
      email: [
        { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
      ],
      address: [
        { max: 200, message: '地址长度不能超过200', trigger: 'blur' }
      ],
      deptId: [
        { required: true, message: '请选择所属部门', trigger: 'blur' }
      ],
      posId: [
        { required: true, message: '请选择职位', trigger: 'blur' }
      ],
      entryDate: [
        { required: true, message: '请选择入职日期', trigger: 'blur' }
      ]
    }

    // 根据部门过滤职位
    const filteredPositions = computed(() => {
      if (!employeeForm.deptId) return positions.value
      return positions.value.filter(pos => pos.deptId === employeeForm.deptId)
    })

    // 格式化日期
    const formatDate = (row, column) => {
      const date = row[column.property]
      if (date) {
        return new Date(date).toLocaleDateString()
      }
      return ''
    }

    // 格式化状态
    const formatStatus = (row, column) => {
      return row.status === 1 ? '在职' : '离职'
    }

    // 获取部门列表
    const loadDepartments = async () => {
      try {
        const response = await getDepartments()
        if (response.code === 200) {
          departments.value = response.data
        }
      } catch (error) {
        ElMessage.error('获取部门列表失败')
      }
    }

    // 获取职位列表
    const loadPositions = async () => {
      try {
        const response = await getPositions()
        if (response.code === 200) {
          positions.value = response.data
        }
      } catch (error) {
        ElMessage.error('获取职位列表失败')
      }
    }

    // 获取员工列表
    const loadEmployees = async () => {
      try {
        const response = await getEmployees()
        if (response.code === 200) {
          employees.value = response.data
          total.value = response.data.length
        }
      } catch (error) {
        ElMessage.error('获取员工列表失败')
      }
    }

    // 搜索员工
    const handleSearch = async () => {
      try {
        const response = await queryEmployees(searchForm)
        if (response.code === 200) {
          employees.value = response.data
          total.value = response.data.length
        }
      } catch (error) {
        ElMessage.error('搜索员工失败')
      }
    }

    // 重置搜索
    const resetSearch = () => {
      Object.assign(searchForm, { empName: '', idCard: '', phone: '', deptId: '', posId: '' })
      loadEmployees()
    }

    // 显示新增对话框
    const showAddDialog = () => {
      dialogType.value = 'add'
      Object.assign(employeeForm, {
        empId: '', empName: '', gender: '', idCard: '', phone: '', email: '', 
        address: '', deptId: '', posId: '', entryDate: '', status: '1'
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
      Object.assign(employeeForm, editData)
      dialogVisible.value = true
    }

    // 部门选择变化处理
    const onDeptChange = async () => {
      employeeForm.posId = ''
      if (employeeForm.deptId) {
        try {
          const response = await getPositionsByDept(employeeForm.deptId)
          if (response.code === 200) {
            // 局部更新职位列表，只针对当前部门
            const deptPositions = response.data
            // 这里可以根据需要进一步处理
          }
        } catch (error) {
          ElMessage.error('获取部门职位失败')
        }
      }
    }

    // 提交表单
    const handleSubmit = async () => {
      if (!employeeFormRef.value) return
      
      await employeeFormRef.value.validate(async (valid) => {
        if (valid) {
          try {
            let response
            if (dialogType.value === 'add') {
              response = await addEmployee(employeeForm)
            } else {
              response = await updateEmployee(employeeForm)
            }
            
            if (response.code === 200) {
              ElMessage.success(response.message)
              dialogVisible.value = false
              loadEmployees()
            } else {
              ElMessage.error(response.message)
            }
          } catch (error) {
            ElMessage.error('操作失败，请稍后重试')
          }
        }
      })
    }

    // 删除员工
    const handleDelete = async (empId) => {
      try {
        await ElMessageBox.confirm('确定要删除该员工吗？', '确认删除', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        const response = await deleteEmployee(empId)
        if (response.code === 200) {
          ElMessage.success(response.message)
          loadEmployees()
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
    }

    const handleCurrentChange = (current) => {
      currentPage.value = current
    }

    // 初始加载
    onMounted(() => {
      loadDepartments()
      loadPositions()
      loadEmployees()
    })

    return {
      employees,
      departments,
      positions,
      filteredPositions,
      searchForm,
      dialogVisible,
      dialogType,
      employeeFormRef,
      employeeForm,
      rules,
      currentPage,
      pageSize,
      total,
      formatDate,
      formatStatus,
      handleSearch,
      resetSearch,
      showAddDialog,
      showEditDialog,
      onDeptChange,
      handleSubmit,
      handleDelete,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.employee-container {
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