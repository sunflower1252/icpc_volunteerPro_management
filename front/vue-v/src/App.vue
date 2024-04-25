<template>
    <myself/>
</template>
<script setup lang="ts">
import { getTest } from '../src/api/ceshi'
import { onMounted } from 'vue'
 import revirse from './views/revirse.vue';
 import myself from './views/myself.vue'
onMounted(() => {
    getTest().then(res => {
        console.log(res)
    })
})
</script>