<template>
    <!-- <revirse/> -->
</template>
<script setup lang="ts">
import { getTest } from '../src/api/ceshi'
import { onMounted } from 'vue'
// import revirse from './views/revirse.vue';
onMounted(() => {
    getTest().then(res => {
        console.log(res)
    })
})
</script>