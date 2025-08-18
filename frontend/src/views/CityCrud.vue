<template>
  <div class="card">
    <!-- Toolbar -->
    <Toolbar class="mb-4">
      <template #start>
        <Button label="New" icon="pi pi-plus" class="mr-2" @click="openNewCityDialog" />
        <Button
          label="Delete"
          icon="pi pi-trash"
          severity="danger"
          class="mr-2"
          @click="deleteSelectedCities"
          :disabled="!selectedCities.length"
        />
      </template>

      <template #end>
        <Button label="Export" icon="pi pi-upload" severity="secondary" @click="exportCSV($event)" />
      </template>
    </Toolbar>

    <!-- DataTable -->
    <DataTable
      ref="dt"
      v-model:selection="selectedCities"
      :value="cities"
      dataKey="city"
      :paginator="true"
      :rows="10"
      :filters="filters"
      :rowsPerPageOptions="[5, 10, 20]"
      paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
      currentPageReportTemplate="Showing {first} to {last} of {totalRecords} cities"
      responsiveLayout="scroll"
      stripedRows
      removableSort
    >
      <!-- Header with Search -->
      <template #header>
        <div class="flex flex-wrap gap-2 items-center justify-between">
          <h4 class="m-0">Manage Cities</h4>
          <IconField>
            <InputIcon>
              <i class="pi pi-search" />
            </InputIcon>
            <InputText v-model="filters['global'].value" placeholder="Search..." />
          </IconField>
        </div>
      </template>

      <Column selectionMode="multiple" headerStyle="width: 3rem" :exportable="false" />
      <Column field="city" header="City ID" sortable />
      <Column field="name" header="Name" sortable />
      <Column field="country" header="Country" sortable />
      <Column field="zone" header="Zone" sortable />
      <Column field="state" header="State" sortable />
      <Column field="area" header="Area" sortable />
      <Column header="Actions" :exportable="false">
        <template #body="{ data }">
          <Button icon="pi pi-pencil" class="mr-2" @click="editCity(data)" />
          <Button icon="pi pi-trash" severity="danger" @click="deleteCity(data.city)" />
        </template>
      </Column>
    </DataTable>

    <!-- Dialog -->
    <Dialog v-model:visible="cityDialog" header="City Form" :modal="true" class="w-1/2">
      <div class="p-fluid">
        <div class="field" v-for="field in Object.keys(cityForm)" :key="field">
          <label :for="field">{{ field }}</label>
          <InputText v-model="cityForm[field]" :id="field" />
        </div>
      </div>

      <template #footer>
        <Button label="Cancel" icon="pi pi-times" @click="closeDialog" class="p-button-text" />
        <Button label="Save" icon="pi pi-check" @click="saveCity" />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAllCities, createCity, updateCity, deleteCityById } from '@/service/city'
import { useToast } from 'primevue/usetoast'

const toast = useToast()
const cities = ref([])
const selectedCities = ref([])
const filters = ref({ global: { value: '', matchMode: 'contains' } })
const cityDialog = ref(false)
const isEdit = ref(false)

const cityForm = ref({
  city: '',
  name: '',
  country: '',
  zone: '',
  state: '',
  area: ''
})

const fetchCities = async () => {
  cities.value = await getAllCities()
}

const openNewCityDialog = () => {
  isEdit.value = false
  cityForm.value = {
    city: '',
    name: '',
    country: '',
    zone: '',
    state: '',
    area: ''
  }
  cityDialog.value = true
}

const editCity = (data) => {
  isEdit.value = true
  cityForm.value = { ...data }
  cityDialog.value = true
}

const closeDialog = () => {
  cityDialog.value = false
}

const saveCity = async () => {
  if (isEdit.value) {
    await updateCity(cityForm.value)
    toast.add({ severity: 'success', summary: 'Updated', detail: 'City updated successfully.' })
  } else {
    await createCity(cityForm.value)
    toast.add({ severity: 'success', summary: 'Created', detail: 'New city added successfully.' })
  }
  cityDialog.value = false
  fetchCities()
}

const deleteCity = async (id) => {
  await deleteCityById(id)
  toast.add({ severity: 'success', summary: 'Deleted', detail: 'City deleted successfully.' })
  fetchCities()
}

const deleteSelectedCities = async () => {
  for (const city of selectedCities.value) {
    await deleteCityById(city.city)
  }
  toast.add({ severity: 'success', summary: 'Deleted', detail: 'Selected cities deleted.' })
  selectedCities.value = []
  fetchCities()
}

onMounted(fetchCities)
</script>

<style scoped>
.field {
  margin-bottom: 1rem;
}
</style>





