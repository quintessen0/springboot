<template>
  <div class="dashboard">
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <h1>人事管理系统</h1>
        </div>
        <div class="header-right">
          <el-button type="danger" @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>
      
      <el-container>
        <el-aside width="200px" class="sidebar">
          <el-menu
            :default-active="activeMenu"
            class="sidebar-menu"
            @select="handleMenuSelect"
          >
            <el-menu-item index="department">
              <el-icon><OfficeBuilding /></el-icon>
              <span>部门管理</span>
            </el-menu-item>
            <el-menu-item index="position">
              <el-icon><User /></el-icon>
              <span>职位管理</span>
            </el-menu-item>
            <el-menu-item index="employee">
              <el-icon><Avatar /></el-icon>
              <span>员工管理</span>
            </el-menu-item>
            <el-menu-item index="attendance">
              <el-icon><Clock /></el-icon>
              <span>考勤管理</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        
        <el-main class="main-content">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { OfficeBuilding, User, Avatar, Clock } from '@element-plus/icons-vue'

export default {
  name: 'Dashboard',
  components: {
    OfficeBuilding,
    User,
    Avatar,
    Clock
  },
  setup() {
    const router = useRouter()
    const activeMenu = ref('department')
    
    const handleMenuSelect = (index) => {
      activeMenu.value = index
      router.push(`/dashboard/${index}`)
    }
    
    const handleLogout = () => {
      localStorage.removeItem('user')
      ElMessage.success('退出登录成功')
      router.push('/login')
    }
    
    return {
      activeMenu,
      handleMenuSelect,
      handleLogout
    }
  }
}
</script>

<style scoped>
.dashboard {
  height: 100vh;
}

.header {
  background-color: #409eff;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.header-left h1 {
  margin: 0;
  font-size: 24px;
}

.sidebar {
  background-color: #f5f5f5;
}

.sidebar-menu {
  border-right: none;
}

.main-content {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
