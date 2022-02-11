<template>
	<v-container fluid class="grey lighten-3" fill-height>

		<v-card class="rounded-xl mx-auto" width="100%" height="42rem" elevation="10">
			<!-- <v-card-title class="indigo white--text text-h5">
				
			</v-card-title> -->
			
			<v-row>
				<v-col>
					<v-card class="rounded-t-xl indigo white--text text-h5" flat tile>
						<toolbar :itemi="active" :snackbar.sync="snackbar" :hint.sync="hint"></toolbar>
					</v-card>
				</v-col>
			</v-row>
			
			<v-row no-gutters>
				<v-col cols="3">
					<file-tree
						:active.sync="active"
					></file-tree>
				</v-col>
				<v-divider vertical></v-divider>
				<v-col>
					<file-info :item="active"></file-info>
				</v-col>
				<v-divider vertical></v-divider>
				<v-col>
					<file-disk></file-disk>
				</v-col>
			</v-row>

		</v-card>
		<v-snackbar
			v-model="snackbar"
			:timeout="6000"
		>
			<span class="text-h5 text-wrapper">{{hint}}</span>

			<template v-slot:action="{ attrs }">
				<v-btn
					color="blue"
					text
					v-bind="attrs"
					@click="snackbar = false"
				>
					关闭
				</v-btn>
			</template>
		</v-snackbar>
	</v-container>
</template>

<script>
	import FileTree from '../components/FileTree.vue'
	import ToolBar from '../components/ToolBar.vue'
	import FileInfo from '../components/FileInfo.vue'
	import FileDisk from '../components/FileDisk.vue'
	export default {
		components: {
			'file-tree': FileTree,
			'toolbar': ToolBar,
			'file-info': FileInfo,
			'file-disk': FileDisk
		},
    mounted() {
      this.getFileItems();
			setTimeout(() => {
				let btn = document.getElementsByClassName('v-treeview-node__toggle')[0]
				btn.click()
			},500)
    },
    data: () => ({
			fileItems: [],
			active: null,
			snackbar: false,
			hint: "hello"
		}),
    methods: {
      getFileItems() {
        fetch("http://localhost:7000/file/check", {
          "method": "GET",
          "headers": {}
        })
        .then(response => response.json())
        .then(json => {
          // this.fileItems = [json];
					this.$store.dispatch('updateFileItems', json)
          console.log(this.fileItems)
        })
        .catch(err => {
          console.error(err);
        });
      }
    }
	}
</script>

<style>
.text-wrapper {
  white-space: pre-wrap;
}
</style>
