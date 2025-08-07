<template>
  <div>
    <h1>📝 Görev Listesi</h1>

    <input v-model="newTask" @keyup.enter="addTask" placeholder="Yeni görev girin..." />
    <button @click="addTask">Ekle</button>

    <ul>
      <li v-for="(task, index) in tasks" :key="index">
        <span :style="{ textDecoration: task.completed ? 'line-through' : 'none' }">
          {{ task.name }}
        </span>
        <button @click="toggleComplete(index)">Tamamlandı</button>
        <button @click="deleteTask(index)">Sil</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newTask: '',
      tasks: []
    };
  },
  created() {
  const savedTasks = localStorage.getItem('tasks');
  if (savedTasks) {
    this.tasks = JSON.parse(savedTasks);
  }
},
  methods: {
    addTask() {
      if (this.newTask.trim() !== '') {
        this.tasks.push({ name: this.newTask, completed: false });
        localStorage.setItem('tasks', JSON.stringify(this.tasks));
        this.newTask = '';
      }
    },
    deleteTask(index) {
      this.tasks.splice(index, 1);
      localStorage.setItem('tasks', JSON.stringify(this.tasks));
    },
    toggleComplete(index) {
      this.tasks[index].completed = !this.tasks[index].completed;
      localStorage.setItem('tasks', JSON.stringify(this.tasks));

    }
  }
};
</script>

<style scoped>
button {
  margin-left: 10px;
}
</style>
