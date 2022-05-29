<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
const friends = ref([])

function loadFriends() {
  // TODO: load friends
}

function goTo(who) {
  router.push("/home/info/friend/" + who)
}
</script>

<template>
<div id='ContactList'>
  <div id="type">
    friends
  </div>
  <div class="group">
    <div class="group-item" v-for="friend in friends" :key="friend" @click="goTo(friend)">
        <div class="head">
          <n-avatar
            round
            :size="48"
            src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
          />
        </div>
        <div class='username'>
            {{ friend }}
        </div>
    </div>
  </div>

</div>
</template>

<style scoped>
#ContactList {
  height: 630px;
  overflow-y: scroll;
  user-select: none;
  margin-left: 2rem;
}
#type {
  font-size: 3rem;
  font-weight: bolder;
}
.group-item {
  margin-top: 1rem;
  margin-right: 8px;
  height: 4rem;
  border-radius: 12px;
  display: flex;
  align-items: flex-start;
  padding-top: 0.5rem;
  transition: all 0.3s;
  cursor: pointer;
}
.group-item:hover {
  background: #ecf0f1;
  padding-left: 1rem;
  height: 4.5rem;
}
.group-item:hover .username {
  font-size: 2rem;
}
.group-item .username {
  font-size: 1.5rem;
  font-weight: bolder;
  margin-left: 1rem;
  transition: all 0.3s;
}
</style>