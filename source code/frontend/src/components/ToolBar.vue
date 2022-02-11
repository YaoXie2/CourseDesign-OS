<template>
  <v-toolbar flat color="rgba(0, 0, 0, 0)">
        <!-- <v-btn @click="createDir">新建目录</v-btn>
        <v-btn @click="createFile" class="ml-2">新建文件</v-btn>
        <v-btn @click="deleteDir" class="ml-2">删除目录</v-btn>
        <v-btn @click="deleteFile" class="ml-2">删除文件</v-btn> -->
    <v-container v-if="!!item" fluid>
      <v-dialog v-if="item.type==='dir'" v-model="createDirDialog" persistent max-width="600px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="primary text-h6 ml-2" dark v-bind="attrs" v-on="on">
            新建目录
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="text-h5">新建目录</span>
          </v-card-title>
          <v-card-text>
            <v-text-field
              v-model="dirName"
              label="请输入目录名"
              hint="不超过3个的字母或数字组合，如d1、dd"
              maxlength="3"
              :rules="[v => v.length <= 3 || '最多3个字母或数字组合']"
              required
            ></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="red darken-1"
              text
              @click="createDirDialog = false"
            >
              取消
            </v-btn>
            <v-btn color="blue darken-1" text @click="createDir">
              确认
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    
      <v-dialog v-if="item.type==='dir'" v-model="createFileDialog" persistent max-width="600px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="primary ml-3 text-h6" dark v-bind="attrs" v-on="on">
            新建文件
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="text-h5">新建文件</span>
          </v-card-title>
          <v-card-text>
            <v-form>
              <v-text-field
              v-model="fileName"
              label="请输入文件名"
              hint="不超过3个的字母或数字组合，如f01、f02"
              maxlength="3"
              :rules="[v => v.length <= 3 || '最多3个字母或数字组合']"
              required
            ></v-text-field>
            <v-select
              v-model="fileType"
              :items="[{'text':'txt文本文件','value':'txt'},
                        {'text':'exe可执行文件','value':'exe'}]"
              item-text="text"
              item-value="value"
              :rules="[v => !!v || 'Item is required']"
              label="文件类型"
              required
            ></v-select>
            <v-textarea
              v-model="fileContent"
              filled
              label="文件内容"
              auto-grow
              :rule="[v => v.length===0 || '请输入文件内容']"
              required
            ></v-textarea>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="red darken-1"
              text
              @click="createFileDialog = false"
            >
              取消
            </v-btn>
            <v-btn color="blue darken-1" text @click="createFile">
              确认
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      
      <v-dialog v-if="!!item" v-model="deleteDialog" persistent max-width="600px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="red ml-3 text-h6" dark v-bind="attrs" v-on="on">
            删除
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="text-h5">删除</span>
          </v-card-title>
          <v-card-text>
            <span class="text-h5">确认删除 {{item.name}} ?</span>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="red darken-1"
              text
              @click="deleteDialog = false"
            >
              取消
            </v-btn>
            <v-btn color="blue darken-1" text @click="deleteTarget">
              确认
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      
      <v-btn v-if="item.type==='exe'" class="primary ml-3 text-h6" @click="execute">
        执行文件
      </v-btn>
      
    </v-container>
    
    <v-container v-else fluid>
      <span class="white--text">请选择一个文件或目录（ / 是根目录）</span>
    </v-container>
  </v-toolbar>
</template>

<script>
export default {
  props: ["itemi"],
  data: () => ({
    dirName: "", // 新建目录名称
    createDirDialog: false, // 创建目录对话框
    
    fileName: "",
    fileType: "txt",
    fileContent: "",
    createFileDialog: false,
    
    deleteDialog: false,
    item: null
  }),
  watch: {
    itemi(newValue) {
      this.item = newValue
    }
  },
  methods: {
    createDir() {
      // console.log(this.getParentPath() + this.dirName)
      if (!!this.dirName && this.dirName.length<=3){
        let formData = new FormData()
        formData.append('path', this.getParentPath() + this.dirName)
        this.doFetch("http://localhost:7000/file/create/dir",formData)
      }
      this.createDirDialog = false
    },
    createFile() {
      // console.log(this.getParentPath()+this.fileName+'.'+this.fileType, this.fileContent);
      if (!!this.fileName && !!this.fileType && this.fileContent) {
        let formData = new FormData()
        formData.append('path', this.getParentPath() + this.fileName + '.' + this.fileType)
        formData.append('content', this.fileContent)
        // console.log(formData)
        this.doFetch("http://localhost:7000/file/create/file",formData)
      } else {
        this.$emit('update:snackbar', true)
        this.$emit('update:hint', "文件名或文件内容为空，不能新建文件！")
      }
      this.createFileDialog=false
    },
    deleteTarget() {
      let formData = new FormData()
      formData.append('path', this.item.path)
      this.doFetch("http://localhost:7000/file/delete",formData)
      this.deleteDialog=false
      this.item=null
    },
    getParentPath() {
      return (this.item.path==='/')?'/':this.item.path+'/'
    },
    doFetch(url,formData) {
      fetch(url, {
          method: "POST",
          mode: "cors",
          body: formData
        }).then(response => response.json())
          .then(json => {
            if (json.error) {
              console.log(json.error)
              this.$emit('update:snackbar', true)
              this.$emit('update:hint', json.error)
            }
            else {
              this.$store.dispatch('updateFileItems', json)
              this.$emit('update:snackbar', true)
              this.$emit('update:hint', "操作成功，目录树已刷新！")
            }
          })
          .catch(err => console.error(err));
    },
    execute() {
      console.log(this.item.content)
      let formData = new FormData()
      formData.append('content',this.item.content)
      fetch("http://localhost:7000/cpu/execute",{
        method: "POST",
        mode: "cors",
        body: formData
      }).then(response => response.json())
        .then(json => {
          if (json.text) {
            this.$emit('update:hint', json.text)
            this.$emit('update:snackbar', true)
          }
          else {
            this.$store.commit('updateInfo',json)
            this.$emit('update:hint', "创建进程 " + json.readyQueue[json.readyQueue.length-1] + " 成功！")
            this.$emit('update:snackbar', true)
          }
        })
        .catch(err => console.log(err))
    }
  },
};
</script>

<style>
</style>