<template>
  <div class="department-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>部门管理</span>
          <el-button type="primary" @click="showAddDialog = true">
            添加部门
          </el-button>
        </div>
      </template>
      
      <el-table :data="departmentList" style="width: 100%">
        <el-table-column prop="deptId" label="部门ID" width="100" />
        <el-table-column prop="deptName" label="部门名称" />
        <el-table-column prop="deptDesc" label="部门描述" />
        <el-table-column prop="createTime" label="创建时间" :formatter="formatDate" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button
              size="small"
              @click="handleEdit(scope.row)"
            >
              编辑
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 添加/编辑部门对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑部门' : '添加部门'"
      width="500px"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="部门描述" prop="deptDesc">
          <el-input
            v-model="form.deptDesc"
            type="textarea"
            placeholder="请输入部门描述"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">
          {{ isEdit ? '更新' : '添加' }}
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getDepartmentList,
  addDepartment,
  updateDepartment,
  deleteDepartment
} from '../api/department'

export default {
  name: 'Department',
  setup() {
    const departmentList = ref([])
    const showAddDialog = ref(false)
    const isEdit = ref(false)
    const formRef = ref()
    
    const form = reactive({
      deptId: null,
      deptName: '',
      deptDesc: ''
    })
    
    const rules = {
      deptName: [
        { required: true, message: '请输入部门名称', trigger: 'blur' }
      ]
    }
    
    // 格式化日期
    const formatDate = (row, column) => {
      const date = row[column.property]
      if (date) {
        // 只显示年月日，格式为 YYYY-MM-DD
        return new Date(date).toLocaleDateString()
      }
      return ''
    }
    
    // 获取部门列表
    const fetchDepartmentList = async () => {
      try {
        const response = await getDepartmentList()
        if (response.code === 200) {
          departmentList.value = response.data
        } else {
          ElMessage.error(response.message)
        }
      } catch (error) {
        ElMessage.error('获取部门列表失败')
      }
    }
    
    // 编辑部门
    const handleEdit = (row) => {
      isEdit.value = true
      form.deptId = row.deptId
      form.deptName = row.deptName
      form.deptDesc = row.deptDesc
      showAddDialog.value = true
    }
    
    // 删除部门
    const handleDelete = async (row) => {
      try {
        await ElMessageBox.confirm('确定要删除这个部门吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        const response = await deleteDepartment(row.deptId)
        if (response.code === 200) {
          ElMessage.success('删除成功')
          fetchDepartmentList()
        } else {
          ElMessage.error(response.message)
        }
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    }
    
    // 提交表单
    const handleSubmit = async () => {
      if (!formRef.value) return
      
      await formRef.value.validate(async (valid) => {
        if (valid) {
          try {
            let response
            if (isEdit.value) {
              response = await updateDepartment(form)
            } else {
              response = await addDepartment(form)
            }
            
            if (response.code === 200) {
              ElMessage.success(response.message)
              showAddDialog.value = false
              resetForm()
              fetchDepartmentList()
            } else {
              ElMessage.error(response.message)
            }
          } catch (error) {
            ElMessage.error('操作失败')
          }
        }
      })
    }
    
    // 重置表单
    const resetForm = () => {
      form.deptId = null
      form.deptName = ''
      form.deptDesc = ''
      isEdit.value = false
    }
    
    onMounted(() => {
      fetchDepartmentList()
    })
    
    return {
      departmentList,
      showAddDialog,
      isEdit,
      formRef,
      form,
      rules,
      formatDate,
      handleEdit,
      handleDelete,
      handleSubmit
    }
  }
}
</script>

<style scoped>
.department-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
