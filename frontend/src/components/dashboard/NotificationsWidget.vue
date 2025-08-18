<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import  Button  from 'primevue/button'
import  Menu  from 'primevue/menu'

/**
 * activities: Backend’den gelen ham log nesneleri
 * items: Menü için (••• tıklanınca açılan)
 * logs: Listelenen bildirim satırları
 */
const menu = ref(null)
const activities = ref([])
const logs = ref([])

/** Menü butonu için örnek seçenekler */
const items = ref([
  { label: 'Mark all as read', icon: 'pi pi-check' },
  { label: 'Clear all',       icon: 'pi pi-trash' }
])

/** Backend’den çek ve logs ref’ine dönüştür */
async function fetchActivityLogs() {
  try {
    const res = await axios.get('http://localhost:8080/api/activity-logs')
    activities.value = res.data

    // Her bir log kaydını HTML içinde göstermek üzere biçimlendiriyoruz
   logs.value = activities.value.map(a => ({
  id: a.id,
  icon: getIcon(a.actionType),
  title: a.description,
  subtitle: `by ${a.username}`,
  time: new Date(a.issueDate).toLocaleString(), // ← DEĞİŞEN SATIR
  actionType: a.actionType
}))
  } catch (err) {
    console.error('Could not load activity logs:', err)
  }
}

/** actionType’a göre ikon seçimi */
function getIcon(actionType) {
  switch (actionType) {
    case 'CREATE': return 'pi pi-plus'
    case 'UPDATE': return 'pi pi-pencil'
    case 'DELETE': return 'pi pi-trash'
    default:       return 'pi pi-info-circle'
  }
}

onMounted(fetchActivityLogs)
</script>

<template>
  <div class="card">
    <div class="flex items-center justify-between mb-6">
      <div class="font-semibold text-xl">Notifications</div>
      <div>
        <Button
          icon="pi pi-ellipsis-v"
          class="p-button-text p-button-plain p-button-rounded"
          @click="menu.toggle($event)"
        />
        <Menu
          ref="menu"
          popup
          :model="items"
          class="!min-w-40"
        />
      </div>
    </div>

    <span class="block text-muted-color font-medium mb-4">RECENT</span>
    <ul class="p-0 m-0 list-none">
      <li
        v-for="log in logs"
        :key="log.id"
        class="flex items-center py-3 border-b border-surface"
      >
        <div
          class="w-12 h-12 flex items-center justify-center bg-blue-100 dark:bg-blue-400/10 rounded-full mr-4 shrink-0"
        >
          <i :class="`${log.icon} !text-xl text-blue-500`" />
        </div>
        <div>
          <div class="text-surface-900 dark:text-surface-0 font-medium">
            {{ log.title }}
          </div>
          <div class="text-surface-700 dark:text-surface-100 text-sm">
            {{ log.subtitle }} • {{ log.time }}
          </div>
        </div>
      </li>
      <li v-if="logs.length === 0" class="py-4 text-center text-surface-500">
        No notifications
      </li>
    </ul>
  </div>
</template>
