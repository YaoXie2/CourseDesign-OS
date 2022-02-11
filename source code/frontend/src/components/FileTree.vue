<template>
  <v-card flat tile width="100%" height="38rem" class="indigo lighten-5 rounded-bl-xl overflow-y-auto">
    <v-treeview
      v-model="tree" 
      :items="items"
      :active.sync="active"
      activatable 
      item-key="path"
      rounded 
      hoverable 
      :return-object="true"
      @update:active="emitActive"
      >
      <template v-slot:prepend="{ item, open }">
        <v-icon v-if="item.type === 'dir'" color="blue">
          {{ open ? 'mdi-folder-open' : 'mdi-folder' }}
        </v-icon>
        <v-icon v-else color="green">
          {{ files[item.type] }}
        </v-icon>
      </template>
<!--      <template slot="label" slot-scope="{item}">
        <a @click="consol.log(item.name)">{{item.name}}</a>
      </template> -->
    </v-treeview>
  </v-card>
</template>

<script>
  export default {
    props: [],
    data: () => ({
      files: {
        txt: 'mdi-file-document-outline',
        exe: 'mdi-rocket'
      },
      active: [],
      tree: []
    }),
    computed: {
      items() {
        return this.$store.state.fileItems;
      }
    },
    methods: {
      emitActive() {
        this.$emit('update:active', this.active[0])
      }
    }
  }
</script>

<style>
</style>
