<template>
<v-container class="fill-height">
	<v-card class="mx-auto">
		<v-card-title>
			磁盘块
		</v-card-title>
		<table>
			<tbody>
				<tr v-for="row in 8" :key="row">
					<td v-for="col in 16" :key="col">
						<v-hover>
							<template v-slot:default="{ hover }">
								<v-card
									:elevation="hover ? 24 : 0"
									:class="{'diskBlock':true,'orange':!!fat[(row-1)*16+col-1]?fat[(row-1)*16+col-1].used:false}"
									tile
									height="2.5rem"
									@click="updateEvent(row,col)"
									>
									{{ (row-1)*16+col-1 }}
								</v-card>
							</template>
						</v-hover>
					</td>
				</tr>
			</tbody>
		</table>
	</v-card>
	
	<v-card class="mx-auto">
		<span>磁盘块 {{select}} 的下一磁盘块：{{nextBlock}}</span>
	</v-card>
	
	<v-card class="mx-auto" v-if="!!block">
		<v-card-title>
			磁盘块 {{select}} 内容
		</v-card-title>
		<table>
			<tbody>
				<tr v-for="i in 4" :key="i">
					<td v-for="j in 16" :key="j">
						<v-hover>
							<template v-slot:default="{ hover }">
								<v-card
									:elevation="hover ? 24 : 0"
									class="diskBlock"
									tile
									width="2rem"
									>
									{{block[(i-1)*16+j-1]}}
								</v-card>
							</template>
						</v-hover>
					</td>
				</tr>
			</tbody>
		</table>
	</v-card>
	<v-card v-else>
		<span>请选择一个磁盘块以查看其内容</span>
	</v-card>
</v-container>
</template>

<script>
	export default {
		data: () => ({
			select: 0,
			nextBlock: 0,
			block: null
		}),
		computed: {
			fat() {
				return this.$store.state.fat
			}
		},
		watch: {
			fat(newValue) {
				// console.log(newValue)
				// console.log(oldValue)
				this.nextBlock=newValue[this.select].next
				let formData = new FormData()
				formData.append('num',this.select)
				fetch("http://localhost:7000/file/diskblock", {
					method: "POST",
					mode: "cors",
					body: formData
				})
					.then((response) => response.json())
					.then(json => {
						if (json.error) console.log(json.error);
						else this.block=json;
					})
					.catch((err) => console.error(err));
			}
		},
		methods: {
			updateEvent(row,col) {
				this.select=(row-1)*16+col-1
				// console.log(this.select)
				this.nextBlock=this.fat[this.select].next
				let formData = new FormData()
				formData.append('num',this.select)
				fetch("http://localhost:7000/file/diskblock", {
					method: "POST",
					mode: "cors",
					body: formData
				})
					.then((response) => response.json())
					.then(json => {
						if (json.error) console.log(json.error);
						else this.block=json;
					})
					.catch((err) => console.error(err));
			}
		}
	}
</script>

<style>
	.diskBlock {
		cursor: pointer;
		background: #ddd;
	}
	td:nth-child(odd) .diskBlock {
		background: #eee;
	}
	.diskBlock:hover {
		background: lightseagreen !important;
	}
	.v-card__title {
		line-height: 1rem;
	}
</style>
