<template>
  <div class="card">
    <!-- Toolbar -->
    <Toolbar class="mb-4">
      <template #start>
        <Button label="New" icon="pi pi-plus" class="mr-2" @click="openNewPortDialog" />
        <Button
          label="Delete"
          icon="pi pi-trash"
          severity="danger"
          class="mr-2"
          @click="deleteSelectedPorts"
          :disabled="!selectedPorts.length"
        />
      </template>

      <template #end>
        <Button label="Export" icon="pi pi-upload" severity="secondary" @click="exportCSV($event)" />
      </template>
    </Toolbar>

    <!-- DataTable -->
    <DataTable
      ref="dt"
      v-model:selection="selectedPorts"
      :value="ports"
      dataKey="code"
      :paginator="true"
      :rows="10"
      :filters="filters"
      :rowsPerPageOptions="[5, 10, 20]"
      paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
      currentPageReportTemplate="Showing {first} to {last} of {totalRecords} ports"
      responsiveLayout="scroll"
      stripedRows
      removableSort
    >
      <!-- Header with Search -->
      <template #header>
        <div class="flex flex-wrap gap-2 items-center justify-between">
          <h4 class="m-0">Manage Ports</h4>
          <IconField>
            <InputIcon>
              <i class="pi pi-search" />
            </InputIcon>
            <InputText v-model="filters['global'].value" placeholder="Search..." />
          </IconField>
        </div>
      </template>

      <Column selectionMode="multiple" headerStyle="width: 3rem" :exportable="false" />
      <Column field="code" header="Port Code" sortable />
      <Column field="name" header="Name" sortable />
      <Column field="cityId" header="City ID" sortable />
      <Column header="Actions" :exportable="false">
        <template #body="{ data }">
          <Button icon="pi pi-pencil" class="mr-2" @click="editPort(data)" />
          <Button icon="pi pi-trash" severity="danger" @click="deletePort(data.code)" />
        </template>
      </Column>
    </DataTable>

    <!-- Dialog -->
    <Dialog v-model:visible="portDialog" header="Port Form" :modal="true" class="w-1/2">
      <div class="p-fluid">
        <div class="field" v-for="field in Object.keys(portForm)" :key="field">
          <label :for="field">{{ field }}</label>
          <InputText v-model="portForm[field]" :id="field" />
        </div>
      </div>

      <template #footer>
        <Button label="Cancel" icon="pi pi-times" @click="closeDialog" class="p-button-text" />
        <Button label="Save" icon="pi pi-check" @click="savePort" />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAllPorts, createPort, updatePort, deletePortByCode } from '@/service/port'
import { useToast } from 'primevue/usetoast'

const toast = useToast()
const ports = ref([])
const selectedPorts = ref([])
const filters = ref({ global: { value: '', matchMode: 'contains' } })
const portDialog = ref(false)
const isEdit = ref(false)

const portForm = ref({
  code: '',
  name: '',
  cityId: ''
})

const fetchPorts = async () => {
  ports.value = await getAllPorts()
}

const openNewPortDialog = () => {
  isEdit.value = false
  portForm.value = {
    code: '',
    name: '',
    cityId: ''
  }
  portDialog.value = true
}

const editPort = (data) => {
  isEdit.value = true
  portForm.value = { ...data }
  portDialog.value = true
}

const closeDialog = () => {
  portDialog.value = false
}

const savePort = async () => {
  try {
    if (isEdit.value) {
      await updatePort(portForm.value.code, {
        name: portForm.value.name,
        cityId: portForm.value.cityId
      })
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Port updated successfully.' })
    } else {
      await createPort(portForm.value)
      toast.add({ severity: 'success', summary: 'Created', detail: 'New port added successfully.' })
    }
    portDialog.value = false
    fetchPorts()
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Operation failed.' })
  }
}

const deletePort = async (code) => {
  try {
    await deletePortByCode(code)
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Port deleted successfully.' })
    fetchPorts()
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Delete operation failed.' })
  }
}

const deleteSelectedPorts = async () => {
  try {
    for (const port of selectedPorts.value) {
      await deletePortByCode(port.code)
    }
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Selected ports deleted.' })
    selectedPorts.value = []
    fetchPorts()
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Delete operation failed.' })
  }
}

onMounted(fetchPorts)
</script>

<style scoped>
.field {
  margin-bottom: 1rem;
}
</style>

