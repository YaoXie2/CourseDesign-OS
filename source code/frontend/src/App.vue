<template>
	<v-app>
		<v-navigation-drawer permanent app width="14rem">
			<v-list-item class="grey lighten-4">
				<v-list-item-content>
					<v-list-item-title class="text-h4 font-weight-black">
						操作系统
					</v-list-item-title>
					<v-list-item-subtitle>
						课程设计
					</v-list-item-subtitle>
				</v-list-item-content>
			</v-list-item>

			<v-divider></v-divider>

			<v-list nav>
				<v-list-item v-for="item in items" :key="item.title" link :to="item.url">
					
					<v-list-item-icon>
						<v-icon large color="indigo">{{ item.icon }}</v-icon>
					</v-list-item-icon>

					<v-list-item-content>
						<v-list-item-title>
							{{item.title}}
						</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>

		<v-main>
			<router-view />
		</v-main>
	</v-app>
</template>

<script>
	export default {
		name: 'App',

		data: () => ({
			items: [{
					title: '文件管理',
					icon: 'mdi-folder-home',
					url: '/'
				},
				{
					title: '调度管理',
					icon: 'mdi-cpu-64-bit',
					url: '/cpu'
				},
				// {
				// 	title: '帮助',
				// 	icon: 'mdi-help',
				// 	url: '/help'
				// },
				{
					title: '关于',
					icon: 'mdi-information',
					url: '/about'
				}
			]
		}),
		computed: {
			snackbar() {
				return this.$store.snackbar
			},
			hint() {
				return this.$store.hint;
			}
		}
	};
</script>
