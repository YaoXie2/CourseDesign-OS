<template>
  <v-container v-if="!!item">
    <v-row>
      <v-col cols=12>
        <v-card height="6rem">
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">
                  名称
                </th>
                <th class="text-left">
                  路径
                </th>
                <th class="text-left">
                  类型
                </th>
                <th class="text-left">
                  起始磁盘块
                </th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{{ item.name }}</td>
                <td>{{ item.path }}</td>
                <td class="text-wrapper">{{ getType(item.type) }}</td>
                <td>{{ item.startDiskBlockNum }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
        </v-card>
      </v-col>
      <v-col cols=12 v-if="item.type==='txt'||item.type==='exe'">
        <v-card height="29rem">
          <v-card-title>
            <v-btn @click="saveFile" dark class="indigo">
              保存文件
            </v-btn>
            <span class="ml-8">字数：{{text.length+'/'+'128'}}</span>
          </v-card-title>
          <v-card-text>
            <v-textarea
              v-model="text"
              filled
              auto-grow
              outlined
              label="编辑文件"
              height="24rem"
              maxlength="128"
              :rules="[v => v.length <= 128 || '最多输入128个字符']"
            ></v-textarea>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col v-else>
        <v-card height="29rem">
          <v-card-text>
            <span class="text-h5">请选择一个文件以编辑</span>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  props:['item'],
  data: () => ({
    // saveBtn: true
  }),
  computed: {
    text: {
      get: function() {
        return this.item===null?"":this.item.content
      },
      set: function(newValue) {
        this.item.content=newValue
      }
    }
  },
  methods: {
    saveFile() {
      // console.log(this.text, this.item.path);
      if (!!this.text && !!this.item.path) {
        let formData = new FormData()
        formData.append('content',this.text)
        formData.append('path',this.item.path)
        fetch("http://localhost:7000/file/modify", {
          method: "POST",
          mode: "cors",
          body: formData
        }).then(response => response.json())
          .then(json => {
            if (json.error) console.log(json.error)
            else this.$store.dispatch('updateFileItems', json)
          })
          .catch(err => console.error(err));
      }
    },
    getType(item) {
      switch (item) {
        case "dir": return "目录"
        case "txt": return "文本\n文件"
        case "exe": return "可执行\n文件"
      }
    }
  }
}
</script>

<style scoped>
.text-wrapper {
  white-space: pre-wrap;
}
</style>