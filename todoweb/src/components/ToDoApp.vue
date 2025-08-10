<template>
  <div class="todo-app">
    <h1>Todo Uygulaması</h1>

    <!-- Login / Register -->
    <div v-if="!currentUser" class="auth">
      <div class="card">
        <h2>Giriş Yap</h2>
        <form @submit.prevent="login">
          <input v-model="loginForm.username" placeholder="Kullanıcı Adı" required />
          <input v-model="loginForm.password" type="password" placeholder="Şifre" required />
          <button type="submit">Giriş</button>
        </form>
      </div>

      <div class="card">
        <h2>Kayıt Ol</h2>
        <form @submit.prevent="register">
          <input v-model="registerForm.username" placeholder="Kullanıcı Adı" required />
          <input v-model="registerForm.password" type="password" placeholder="Şifre" required />
          <button type="submit">Kayıt Ol</button>
        </form>
      </div>
    </div>

    <!-- Tasks -->
    <div v-else class="tasks">
      <div class="bar">
        <div>Hoş geldin, <b>{{ currentUser.username }}</b> (ID: {{ currentUser.id }})</div>
        <button class="secondary" @click="logout">Çıkış</button>
      </div>

      <div class="add">
        <input v-model="newTask.title" placeholder="Görev başlığı" />
        <input v-model="newTask.description" placeholder="Görev açıklaması (opsiyonel)" />
        <button @click="addTask">Ekle</button>
      </div>

      <div class="list">
        <div v-for="task in tasks" :key="task.id" class="item">
          <div class="info">
            <div class="title">{{ task.title }}</div>
            <div class="desc" v-if="task.description">{{ task.description }}</div>
          </div>
          <div class="actions">
            <select v-model="task.status" @change="updateTaskStatus(task.id, task.status)">
              <option value="TO_DO">Yapılacak</option>
              <option value="IN_PROGRESS">Devam Ediyor</option>
              <option value="COMPLETED">Tamamlandı</option>
              <option value="CANCELLED">İptal Edildi</option>
            </select>
            <button class="danger" @click="deleteTask(task.id)">Sil</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

const API = 'http://localhost:8080/api'

export default {
  name: 'TodoApp',
  data() {
    return {
      currentUser: null,
      tasks: [],
      loginForm: { username: '', password: '' },
      registerForm: { username: '', password: '' },
      newTask: { title: '', description: '', userId: null }
    }
  },
  methods: {
    // Kullanıcı işlemleri
    async register() {
      try {
        const { data } = await axios.post(`${API}/user/register`, this.registerForm)
        this.currentUser = data
        this.newTask.userId = data.id
        this.registerForm = { username: '', password: '' }
        await this.loadTasks()
      } catch (e) {
        alert('Kayıt hatası: ' + (e.response?.data?.message || e.message))
      }
    },
    async login() {
      try {
        const { data } = await axios.post(`${API}/user/login`, this.loginForm)
        if (!data || !data.id) throw new Error('Kullanıcı bulunamadı')
        this.currentUser = data
        this.newTask.userId = data.id
        this.loginForm = { username: '', password: '' }
        await this.loadTasks()
      } catch (e) {
        alert('Giriş hatası: ' + (e.response?.data?.message || e.message))
      }
    },
    logout() {
      this.currentUser = null
      this.tasks = []
      this.newTask = { title: '', description: '', userId: null }
    },

    // Task işlemleri
    async loadTasks() {
      if (!this.currentUser) return
      try {
        const { data } = await axios.get(`${API}/tasks/user/${this.currentUser.id}`)
        this.tasks = data
      } catch (e) {
        console.error('Görevler yüklenemedi:', e)
      }
    },
    async addTask() {
      if (!this.newTask.title.trim()) return
      if (!this.currentUser) return alert('Önce giriş yapın')
      try {
        this.newTask.userId = this.currentUser.id
        const { data } = await axios.post(`${API}/tasks`, this.newTask)
        this.tasks.push(data)
        this.newTask = { title: '', description: '', userId: this.currentUser.id }
      } catch (e) {
        alert('Görev eklenemedi: ' + (e.response?.data?.message || e.message))
      }
    },
    async updateTaskStatus(taskId, status) {
      try {
        const { data } = await axios.put(`${API}/tasks/${taskId}/status`, { status })
        const i = this.tasks.findIndex(t => t.id === taskId)
        if (i !== -1) this.tasks[i] = data
      } catch (e) {
        alert('Durum güncellenemedi: ' + (e.response?.data?.message || e.message))
      }
    },
    async deleteTask(taskId) {
      try {
        await axios.delete(`${API}/tasks/${taskId}`)
        this.tasks = this.tasks.filter(t => t.id !== taskId)
      } catch (e) {
        alert('Görev silinemedi: ' + (e.response?.data?.message || e.message))
      }
    }
  }
}
</script>

<style scoped>
.todo-app { max-width: 900px; margin: 0 auto; padding: 24px; }
.auth { display: grid; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); gap: 16px; }
.card { border: 1px solid #e5e7eb; border-radius: 8px; padding: 16px; }
.card form { display: grid; gap: 10px; }
.tasks .bar { display: flex; justify-content: space-between; align-items: center; margin: 12px 0 20px; }
.add { display: grid; grid-template-columns: 1fr 1fr auto; gap: 10px; margin-bottom: 16px; }
.list { display: grid; gap: 10px; }
.item { display: flex; justify-content: space-between; align-items: center; border: 1px solid #e5e7eb; padding: 12px; border-radius: 8px; }
.info .title { font-weight: 600; }
.info .desc { color: #6b7280; font-size: 14px; }
.actions { display: flex; gap: 8px; align-items: center; }
input, select { padding: 10px; border: 1px solid #d1d5db; border-radius: 6px; }
button { padding: 10px 14px; border: 0; border-radius: 6px; cursor: pointer; background: #2563eb; color: white; }
button.secondary { background: #6b7280; }
button.danger { background: #dc2626; }
button:hover { filter: brightness(0.95); }
</style>
