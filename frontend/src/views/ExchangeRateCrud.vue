<template>
  <div class="card">
    <!-- Toolbar -->
    <Toolbar class="mb-4">
      <template #start>
        <Button label="New" icon="pi pi-plus" class="mr-2" @click="openNewExchangeDialog" />
        <Button
          label="Delete"
          icon="pi pi-trash"
          severity="danger"
          class="mr-2"
          @click="deleteSelectedExchangeRates"
          :disabled="!selectedExchangeRates.length"
        />
      </template>

      <template #end>
        <Button label="Export" icon="pi pi-upload" severity="secondary" @click="exportCSV($event)" />
      </template>
    </Toolbar>

    <!-- DataTable -->
    <DataTable
      ref="dt"
      v-model:selection="selectedExchangeRates"
      :value="exchangeRates"
      dataKey="id"
      :paginator="true"
      :rows="10"
      :filters="filters"
      :rowsPerPageOptions="[5, 10, 20]"
      paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
      currentPageReportTemplate="Showing {first} to {last} of {totalRecords} exchange rates"
      responsiveLayout="scroll"
      stripedRows
      removableSort
    >
      <template #header>
        <div class="flex flex-wrap gap-2 items-center justify-between">
          <h4 class="m-0">Manage Exchange Rates</h4>
          <IconField>
            <InputIcon>
              <i class="pi pi-search" />
            </InputIcon>
            <InputText v-model="filters['global'].value" placeholder="Search..." />
          </IconField>
        </div>
      </template>

      <Column selectionMode="multiple" headerStyle="width: 3rem" :exportable="false" />
      <Column field="exchangeDate" header="Date" sortable :filter="true" :filterMatchMode="'equals'" :dataType="'date'" />
      <Column field="fromCurr" header="From Currency" sortable />
      <Column field="toCurr" header="To Currency" sortable />
      <Column field="rate" header="Rate" sortable />
      <Column field="rateType" header="Rate Type" sortable />
      <Column header="Actions" :exportable="false" style="width: 8rem">
        <template #body="{ data }">
          <Button icon="pi pi-pencil" class="mr-2" @click="editExchangeRate(data)" />
          <Button icon="pi pi-trash" severity="danger" @click="deleteExchangeRate(data.id)" />
        </template>
      </Column>
    </DataTable>

    <!-- Dialog -->
    <Dialog v-model:visible="exchangeDialog" header="Exchange Rate Form" :modal="true" class="w-1/2" :closable="false">
      <div class="p-fluid">
        <div class="field">
          <label for="exchangeDate">Date</label>
          <Calendar id="exchangeDate" v-model="exchangeForm.exchangeDate" dateFormat="yy-mm-dd" />
        </div>

        <div class="field">
          <label for="fromCurr">From Currency</label>
          <InputText id="fromCurr" v-model="exchangeForm.fromCurr" />
        </div>

        <div class="field">
          <label for="toCurr">To Currency</label>
          <InputText id="toCurr" v-model="exchangeForm.toCurr" />
        </div>

        <div class="field">
          <label for="rate">Rate</label>
          <InputNumber id="rate" v-model="exchangeForm.rate" mode="decimal" :min="0" :max="999999" />
        </div>

        <div class="field">
          <label for="rateType">Rate Type</label>
          <InputText id="rateType" v-model="exchangeForm.rateType" />
        </div>
      </div>

      <template #footer>
        <Button label="Cancel" icon="pi pi-times" @click="closeDialog" class="p-button-text" />
        <Button label="Save" icon="pi pi-check" @click="saveExchangeRate" />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import Calendar from 'primevue/calendar';
import InputNumber from 'primevue/inputnumber'; // ✔️ Bu çalışır
import { getAllExchangeRates, createExchangeRate, updateExchangeRate, deleteExchangeRateById } from '@/service/exchangeRate'
import { useToast } from 'primevue/usetoast'

const toast = useToast()
const exchangeRates = ref([])
const selectedExchangeRates = ref([])
const filters = ref({ global: { value: '', matchMode: 'contains' } })
const exchangeDialog = ref(false)
const isEdit = ref(false)

const exchangeForm = ref({
  id: null,
  exchangeDate: null,
  fromCurr: '',
  toCurr: '',
  rate: null,
  rateType: ''
})

const fetchExchangeRates = async () => {
  exchangeRates.value = await getAllExchangeRates()
}

const openNewExchangeDialog = () => {
  isEdit.value = false
  exchangeForm.value = {
    id: null,
    exchangeDate: null,
    fromCurr: '',
    toCurr: '',
    rate: null,
    rateType: ''
  }
  exchangeDialog.value = true
}

const editExchangeRate = (data) => {
  isEdit.value = true
  exchangeForm.value = { ...data }
  exchangeDialog.value = true
}

const closeDialog = () => {
  exchangeDialog.value = false
}

const saveExchangeRate = async () => {
  try {
    if (isEdit.value) {
      await updateExchangeRate(exchangeForm.value.id, {
        exchangeDate: exchangeForm.value.exchangeDate,
        fromCurr: exchangeForm.value.fromCurr,
        toCurr: exchangeForm.value.toCurr,
        rate: exchangeForm.value.rate,
        rateType: exchangeForm.value.rateType
      })
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Exchange rate updated successfully.' })
    } else {
      await createExchangeRate(exchangeForm.value)
      toast.add({ severity: 'success', summary: 'Created', detail: 'New exchange rate added successfully.' })
    }
    exchangeDialog.value = false
    fetchExchangeRates()
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Operation failed.' })
  }
}

const deleteExchangeRate = async (id) => {
  try {
    await deleteExchangeRateById(id)
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Exchange rate deleted successfully.' })
    fetchExchangeRates()
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Delete operation failed.' })
  }
}

const deleteSelectedExchangeRates = async () => {
  try {
    for (const item of selectedExchangeRates.value) {
      await deleteExchangeRateById(item.id)
    }
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Selected exchange rates deleted.' })
    selectedExchangeRates.value = []
    fetchExchangeRates()
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Delete operation failed.' })
  }
}

onMounted(fetchExchangeRates)
</script>

<style scoped>
.field {
  margin-bottom: 1rem;
}
</style>

