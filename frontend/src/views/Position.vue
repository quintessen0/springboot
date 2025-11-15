<template>
  <div class="position-container">
    <h2>职位管理</h2>
    
    <!-- 搜索和添加按钮 -->
    <div class="header-section">
      <el-select v-model="searchDeptId" placeholder="选择部门" style="width: 200px; margin-right: 10px;" @change="handleDeptChange">
        <el-option label="全部" value="" />
        <el-option v-for="dept in departments" :key="dept.deptId" :label="dept.deptName" :value="dept.deptId" />
      </el-select>
      <el-button type="primary" @click="showAddDialog">新增职位</el-button>
    </div>

    <!-- 职位表格 -->
    <el-table :data="positions" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="posId" label="职位ID" width="80" />
      <el-table-column prop="posName" label="职位名称" />
      <el-table-column prop="posDesc" label="职位描述" />
      <el-table-column prop="department.deptName" label="所属部门" />
      <el-table-column prop="createTime" label="创建时间" width="180" :formatter="formatDate" />
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="showEditDialog(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.posId)">删除</el-button>
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
      :title="dialogType === 'add' ? '新增职位' : '编辑职位'"
      width="500px"
    >
      <el-form
        ref="positionFormRef"
        :model="positionForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="职位名称" prop="posName">
          <el-input v-model="positionForm.posName" placeholder="请输入职位名称" />
        </el-form-item>
        <el-form-item label="职位描述" prop="posDesc">
          <el-input v-model="positionForm.posDesc" placeholder="请输入职位描述" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="所属部门" prop="deptId">
          <el-select v-model="positionForm.deptId" placeholder="请选择部门" style="width: 100%;">
            <el-option v-for="dept in departments" :key="dept.deptId" :label="dept.deptName" :value="dept.deptId" />
          </el-select>
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
import { getPositions, getPositionsByDept, addPosition, updatePosition, deletePosition } from '../api/position'
import { getDepartments } from '../api/department'

export default {
  name: 'Position',
  setup() {
    // 数据
    const positions = ref([])
    const departments = ref([])
    const searchDeptId = ref('')
    const dialogVisible = ref(false)
    const dialogType = ref('add')
    const positionFormRef = ref(null)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    // 表单数据
    const positionForm = reactive({
      posId: '',
      posName: '',
      posDesc: '',
      deptId: ''
    })

    // 表单验证规则
    const rules = {
      posName: [
        { required: true, message: '请输入职位名称', trigger: 'blur' },
        { min: 1, max: 50, message: '职位名称长度在1-50之间', trigger: 'blur' }
      ],
      posDesc: [
        { max: 200, message: '职位描述长度不能超过200', trigger: 'blur' }
      ],
      deptId: [
        { required: true, message: '请选择所属部门', trigger: 'blur' }
      ]
    }

    // 格式化日期
    const formatDate = (row, column) => {
      const date = row[column.property]
      if (date) {
        return new Date(date).toLocaleString()
      }
      return ''
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
        let response
        if (searchDeptId.value) {
          response = await getPositionsByDept(searchDeptId.value)
        } else {
          response = await getPositions()
        }
        
        if (response.code === 200) {
          positions.value = response.data
          total.value = response.data.length
        }
      } catch (error) {
        ElMessage.error('获取职位列表失败')
      }
    }

    // 显示新增对话框
    const showAddDialog = () => {
      dialogType.value = 'add'
      Object.assign(positionForm, { posId: '', posName: '', posDesc: '', deptId: '' })
      dialogVisible.value = true
    }

    // 显示编辑对话框
    const showEditDialog = (row) => {
      dialogType.value = 'edit'
      Object.assign(positionForm, row)
      dialogVisible.value = true
    }

    // 提交表单
    const handleSubmit = async () => {
      if (!positionFormRef.value) return
      
      await positionFormRef.value.validate(async (valid) => {
        if (valid) {
          try {
            let response
            if (dialogType.value === 'add') {
              response = await addPosition(positionForm)
            } else {
              response = await updatePosition(positionForm)
            }
            
            if (response.code === 200) {
              ElMessage.success(response.message)
              dialogVisible.value = false
              loadPositions()
            } else {
              ElMessage.error(response.message)
            }
          } catch (error) {
            ElMessage.error('操作失败，请稍后重试')
          }
        }
      })
    }

    // 删除职位
    const handleDelete = async (posId) => {
      try {
        await ElMessageBox.confirm('确定要删除该职位吗？', '确认删除', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        const response = await deletePosition(posId)
        if (response.code === 200) {
          ElMessage.success(response.message)
          loadPositions()
        } else {
          ElMessage.error(response.message || '删除失败')
        }
      } catch (error) {
        // 用户取消删除或网络错误
        if (error !== 'cancel') {
          ElMessage.error('删除失败：' + (error.message || '网络错误或服务器异常'))
        }
      }
    }

    // 分页处理
    const handleSizeChange = (size) => {
      pageSize.value = size
    }

    const handleCurrentChange = (current) => {
      currentPage.value = current
    }

    // 监听部门选择变化
    const handleDeptChange = () => {
      currentPage.value = 1
      loadPositions()
    }
    
    // 初始加载
    onMounted(() => {
      loadDepartments()
      loadPositions()
    })

    return {
      positions,
      departments,
      searchDeptId,
      dialogVisible,
      dialogType,
      positionFormRef,
      positionForm,
      rules,
      currentPage,
      pageSize,
      total,
      formatDate,
      showAddDialog,
      showEditDialog,
      handleSubmit,
      handleDelete,
      handleSizeChange,
      handleCurrentChange,
      handleDeptChange
    }
  }
}
</script>

<style scoped>
.position-container {
  padding: 20px;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>