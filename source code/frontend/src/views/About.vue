<template>
<v-container class="grey lighten-3" fluid fill-height>
  <v-row class="text-center">
    <v-col cols="12">
      <v-img
        :src="require('../../public/logo.svg')"
        class="my-3"
        contain
        height="200"
      />
    </v-col>
    <v-col class="mb-2">
      <h1 class="display-2 font-weight-bold mb-3">
        2019级 操作系统 <span class="indigo--text">课程设计</span>
      </h1>
      <h2>
        2021年11月1日
      </h2>
      <br>
      <h2>
        谢育浩  黄梓豪  宋雨倩  陈琳  黄俊鸿  支永健
      </h2>
    </v-col>
  </v-row>
</v-container>
</template>

<script>
export default {
  data: () => ({
    mates: [
      {id:'201925220524',name:'谢育浩'},
      {id:'201925220508',name:'黄梓豪'},
      {id:'201919210118',name:'宋雨倩'},
      {id:'201925220603',name:'陈琳'},
      {id:'201925220613',name:'黄俊鸿'},
      {id:'201925220629',name:'支永健'},
    ]
  })
}
</script>
