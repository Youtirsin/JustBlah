<template>
  <div id="search-window">
    <div id="search">
      <n-input size="large" autosize 
          style="background:#f2f2f2;min-width: 90%;" 
          @keydown.enter="goTo('/home/search/search/' + keyword)" v-model:value="keyword"
          round placeholder="Type to search">
        <template #prefix>
          <n-icon size="25">
            <Search />
          </n-icon>
        </template>
      </n-input>
    </div>
    <div id="title">
      '{{ keyword }}'的搜索结果
    </div>
    <div id="tab">
      <n-tabs type="segment" size="large">
        <n-tab-pane name="联系人" tab="联系人">
          <div class="list">
              <div class="list-item" v-for="name in results" 
              v-bind:key="name" @click="goTo('/home/search/userinfo/' + name)">
                  <div class="head">
                    <n-avatar
                      round
                      :size="48"
                      src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
                    />
                  </div>
                  <div class='username'>
                      {{ name }}
                  </div>
              </div>
          </div>
        </n-tab-pane>
        <n-tab-pane name="群聊" tab="群聊">
          <div class="list">
              <div class="list-item" v-for="name in groupresults" 
              v-bind:key="name" @click="goTo('/home/search/groupinfo/' + name)">
                  <div class="head">
                    <n-avatar
                      round
                      :size="48"
                      src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
                    />
                  </div>
                  <div class='username'>
                      {{ name }}
                  </div>
              </div>
          </div>
        </n-tab-pane>
      </n-tabs>
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, getCurrentInstance, watch } from 'vue'
import { useMessage } from 'naive-ui'
import { useRouter } from 'vue-router'


import { Search } from "@vicons/tabler"

export default defineComponent({
  components: {
    Search
  },
  setup() {
    const router = useRouter()
    const proxy = getCurrentInstance().proxy
    const message = useMessage()
    const keyword = ref("")

    
    keyword.value = router.currentRoute.value.params.type

    watch(()=> router.currentRoute.value.params, ()=> {
      keyword.value = router.currentRoute.value.params.type
      search()
      searchGroup()
    })

    const results = ref([])
    function search() {
      proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.SearchUser,
        params: {
          keyword : keyword.value
        }
      })
      .then(response => {
        results.value = []
        if (response.data.code == 200) {
          console.log(response.data.data)
          response.data.data.forEach(el => {
            results.value.push(el)
          })
        }else if (response.data.code == 2001) {
          message.error("用户未登录")
          router.push("/login/signin")
        }
      })
    }
    
    const groupresults = ref([])
    function searchGroup() {
      proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.SearchGroup,
        params: {
          keyword : keyword.value
        }
      })
      .then(response => {
        groupresults.value = []
        if (response.data.code == 200) {
          console.log(response.data.data)
          response.data.data.forEach(el => {
            groupresults.value.push(el)
          })
        }else if (response.data.code == 2001) {
          message.error("用户未登录")
          router.push("/login/signin")
        }
      })
    }
    
    search()
    searchGroup()

    return {
      keyword,
      results,
      groupresults,
      
      goTo(path) {
        router.push(path)
      }
    }
  }
})
</script>

<style scoped>
#search-window {
    height: 630px;
    overflow: scroll;
}
#search {
  display: flex;
  justify-content: center;
  padding: 1rem 0;
}
#title {
  font-size: 2.4rem;
  font-weight: bolder;
  padding: 0 2rem;
}
#tab {
  padding: 0 2rem;
}
.list {
  padding-left: 2rem;
}
.list-item {
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
.list-item:hover {
  background: #ecf0f1;
  padding-left: 1rem;
  height: 4.5rem;
}
.list-item:hover .username {
  font-size: 2rem;
}
.list-item .username {
  font-size: 1.5rem;
  font-weight: bolder;
  margin-left: 1rem;
  transition: all 0.3s;
}
</style>
