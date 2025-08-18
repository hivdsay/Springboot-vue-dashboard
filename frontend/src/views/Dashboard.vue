<script setup>
import BestSellingWidget from '@/components/dashboard/BestSellingWidget.vue'
import NotificationsWidget from '@/components/dashboard/NotificationsWidget.vue'
import LastExchangeWidget from '@/components/dashboard/LastExchangeWidget.vue'
import StatsWidget from '@/components/dashboard/StatsWidget.vue'
import CityPortRangeWidget from '@/components/dashboard/CityPortRangeWidget.vue' // ← yeni grafik
</script>

<template>
  <div class="grid grid-cols-12 gap-8">
    <!-- Üstteki istatistik kutuları -->
    <div class="col-span-12">
      <StatsWidget />
    </div>

    <!-- Sol taraf: Satış ve ürünler -->
    <div class="col-span-12 xl:col-span-6 flex flex-col gap-4 justify-stretch">
      <LastExchangeWidget />
      <BestSellingWidget />
    </div>

    <!-- Sağ taraf: Bildirimler ve grafik -->
    <div class="col-span-12 xl:col-span-6 flex flex-col gap-4 justify-stretch">
      <CityPortRangeWidget />
      <NotificationsWidget />

    </div>
  </div>
</template>

