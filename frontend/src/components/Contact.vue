<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const contactActive = ref('')

function goTo(part) {
  contactActive.value = part
  router.push("/home/contact/" + part)
}

</script>

<template>
  <div>
    <div v-bind:class="{ 'list-item': true, 'list-item-active': contactActive == 'friend' }" @click="goTo('friend')">
      好友
    </div>
    <div class="divider"></div>
    <div v-bind:class="{ 'list-item': true, 'list-item-active': contactActive == 'group' }" @click="goTo('group')">
      群聊
    </div>
  </div>
</template>

<style scoped>
.list-item {
  margin: 5px 8px 5px 8px;
  height: 4rem;
  border-radius: 12px;
  display: flex;
  align-content: flex-start;
  padding-top: 0.5rem;
  padding-left: 1rem;
  transition: all 0.3s;
  user-select: none;
  font-size: 1.6rem;
  font-weight: bolder;
}
.list-item:hover {
  background: #ecf0f1;
  height: 4.5rem;
  font-size: 2.4rem;
}
.list-item-active {
  background: #ecf0f1;
  height: 4.5rem;
  font-size: 2.4rem;
}
.divider {
  border-bottom: solid 2px #ecf0f1;
}
</style>