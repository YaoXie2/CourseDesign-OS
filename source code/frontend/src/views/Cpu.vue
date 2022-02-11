<template>
	<v-container fluid class="grey lighten-3" fill-height>

		<v-card class="rounded-xl mx-auto" width="100%" height="72rem" elevation="5">
			<v-container fill-height fluid class="text-center">
        <v-row>
          <!-- rem第一列 -->
          <v-col>
            <v-row>
              <!-- rem功能控制 -->
              <v-col cols=12>
                <v-card class="teal lighten-5 fill-height" height="19rem">
                  <div v-if="!auto">
                  <v-card-text>
                    <v-btn @click="nextSlice" class="indigo" dark>手动执行下一个时间片</v-btn>
                  </v-card-text>
                  <v-card-text>
                      <v-btn @click="autoSlice" class="indigo" dark>
                        自动执行时间片
                      </v-btn>
                      <v-select
                        v-model="speed"
                        :items='[{text:"时间片：快 500ms",value:500},
                                  {text:"时间片：中 1000ms",value:1000},
                                  {text:"时间片：慢 1500ms",value:1500}]'
                        item-text="text"
                        item-value="value"
                      ></v-select>
                  </v-card-text>
                  </div>
                  <div v-else>
                  <v-card-text>
                    <v-btn @click="pauseSlice" class="indigo" dark>
                      暂停
                    </v-btn>
                  </v-card-text>
                  </div>
                  <v-card-text class="black--text">
                    <span class="text-h5">第 </span>
                    <span class="text-h3 red--text">{{slice}}</span>
                    <span class="text-h5"> 时间片</span>
                  </v-card-text>
                </v-card>
              </v-col>
              <!-- rem内存占用率 -->
              <v-col cols=12 class="text-center">
                <v-card class="orange lighten-5 fill-height" height="19rem">
                  <v-card-text v-if="info">
                    <v-progress-circular
                      :size="200"
                      :width="30"
                      :value="info.occupyMemory/512*100"
                      color="primary"
                    >
                      <div class="text-h6">
                        {{info.occupyMemory}}/{{512}}字节
                      </div>
                    </v-progress-circular>
                  </v-card-text>
                  <v-card-text>
                    <div class="text-h5 black--text">内存占用率</div>
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-col>
          
          <!-- rem进程信息 -->
          <v-col>
            <v-card v-if="pcb" class="green lighten-5 fill-height">
              <v-card-title>
                进程 <span class="text-h3 mx-1 red--text">#{{pcb.pid}}</span> 信息
              </v-card-title>
              <div class="pt-2">
                <v-simple-table>
                  <template v-slot:default>
                    <thead>
                      <tr>
                        <th>x寄存器</th>
                        <th>指令计数器</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-ripple>
                        <td class="text-h6">{{ pcb.ax }}</td>
                        <td class="text-h6">{{ pcb.pc }}</td>
                      </tr>
                    </tbody>
                  </template>
                </v-simple-table>
              </div>
              <div class="pt-2">
                <v-simple-table fixed-header>
                  <template v-slot:default>
                    <thead>
                      <tr>
                        <th>行号</th>
                        <th>指令</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="no in pcb.instructions.length" :key="no"
                        :class="{'purple lighten-3':(no-1)===pcb.pc}"
                        v-ripple
                      >
                        <td class="text-h6">{{no-1}}</td>
                        <td class="text-h5">{{pcb.instructions[no-1]}}</td>
                      </tr>
                    </tbody>
                  </template>
                </v-simple-table>
              </div>
            </v-card>
            <v-card v-else class="green lighten-5 fill-height">
              <v-card-text>
                <div class="text-h5">在进程队列<br>选择一个进程<br>以查看信息</div>
              </v-card-text>
            </v-card>  
          </v-col>
          
          <!-- rem就绪队列 -->
          <v-col>
            <v-card class="green lighten-5 fill-height">
              <v-card-title>
                进程队列
              </v-card-title>
              <v-simple-table fixed-header>
                  <template v-slot:default>
                    <thead>
                      <tr>
                        <td>进程编号</td>
                        <td>状态</td>
                      </tr>
                    </thead>
                    <tbody v-if="info">
                      <!-- <tr v-for="key in info.readyQueue.length" :key="info.readyQueue[key-1]"
                          @click="showPcb(key)"
                          :class="{'orange lighten-3':key===1}"
                      >
                        <td class="text-h6">{{key-1}}</td>
                        <td class="text-h5">#{{info.readyQueue[key-1]}}</td>
                      </tr> -->
                      <tr v-for="pid in info.readyQueue" :key="pid"
                          @click="showPcb(pid)"
                          :class="{'orange lighten-3':pid===info.readyQueue[0]||info.pcbs[pid].processState===-1}"
                          v-ripple
                      >
                        <td class="text-h5">#{{pid}}</td>
                        <td class="text-h5">{{getState(info.pcbs[pid].processState)}}</td>
                      </tr>
                    </tbody>
                  </template>
                </v-simple-table>
            </v-card>
          </v-col>
          
          <v-col>
            <v-row>
              <!-- rem阻塞队列 -->
              <v-col cols=12>
                <v-card class="green lighten-5 fill-height">
                  <v-card-title>
                    阻塞队列
                  </v-card-title>
                  <v-simple-table dense fixed-header height="14rem">
                      <template v-slot:default>
                        <thead>
                          <tr>
                            <th>序号</th>
                            <th>进程编号</th>
                          </tr>
                        </thead>
                        <tbody v-if="!!info && !!info.blockedQueue">
                          <tr v-for="pid in info.blockedQueue" :key="pid"
                              @click="pcb=info.pcbs[pid]"
                              v-ripple
                          >
                            <td>#{{pid}}</td>
                            <td>{{getState(info.pcbs[pid].processState)}}</td>
                          </tr>
                        </tbody>
                      </template>
                    </v-simple-table>
                </v-card>
              </v-col>
              <!-- rem设备管理 -->
              <v-col cols=12>
                <v-card class="blue lighten-4 fill-height" height="21rem">
                  <v-card-title>
                    设备占用
                  </v-card-title>
                  
                  <v-simple-table dense fixed-header height="14rem">
                    <template v-slot:default>
                      <thead>
                        <tr>
                          <th>序号</th>
                          <th>使用中的进程编号</th>
                        </tr>
                      </thead>
                      <tbody v-if="!!info && !!info.blockedQueue">
                        <tr height="50rem">
                          <td class="text-h5">设备A</td>
                          <td>
                            <v-chip  v-for="key in info.deviceAQueue.length" :key="key"
                              class="red lighten-4"
                            >
                              #{{info.deviceAQueue[key-1]}}
                            </v-chip>
                          </td>
                        </tr>
                        <tr height="50rem">
                          <td class="text-h5">设备B</td>
                          <td>
                            <v-chip  v-for="key in info.deviceBQueue.length" :key="key"
                              class="red lighten-4"
                            >
                              #{{info.deviceBQueue[key-1]}}
                            </v-chip>
                          </td>
                        <tr height="50rem">
                          <td class="text-h5">设备C</td>
                          <td>
                            <v-chip  v-for="key in info.deviceCQueue.length" :key="key"
                              class="red lighten-4"
                            >
                              #{{info.deviceCQueue[key-1]}}
                            </v-chip>
                          </td>
                        </tr>
                      </tbody>
                    </template>
                  </v-simple-table>
                  
                </v-card>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        
        <!-- 内存单元 -->
        <v-row>
          <v-col>
            <v-card class="teal lighten-3">
              <v-card-title class="teal lighten-4">
                内存单元（共512字节）
              </v-card-title>
              <v-card-text class="mt-2">
              <table>
                <tbody>
                  <tr v-for="i in 16" :key="i">
                    <td v-for="j in 32" :key="j">
                      <v-hover>
                        <template v-slot:default="{ hover }">
                          <v-card
                            v-if="!!info"
                            :elevation="hover ? 24 : 0"
                            :class="['diskBlock', getColor(info.mem[(i-1)*32+j-1].num)]"
                            tile
                            width="2.4rem"
                            >
                            {{(i-1)*32+j-1}}
                          </v-card>
                        </template>
                      </v-hover>
                    </td>
                  </tr>
                </tbody>
              </table>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
			
      </v-container>
		</v-card>
	</v-container>
</template>

<script>
	export default {
		components: {
		},
    mounted() {
      this.getInfo()
    },
    data: () => ({
      pcb: null,
      colors: ['orange','green','blue','light-green','purple','light-blue','purple','pink','lime','yellow','white'],
      timer: 0,
      slice: 0,
      speed: 500,
      auto: false
		}),
    computed: {
      info() {
        return this.$store.state.info;
      }
    },
    methods: {
      getRandomInt() {
        return Math.floor(Math.random()*(50-5+1)+5)
      },
      getInfo() {
        fetch("http://localhost:7000/cpu/getinfo", {
          "method": "GET",
        })
        .then(response => response.json())
        .then(json => {
          if (json.text) console.log(json.text)
          else {this.$store.commit('updateInfo',json)}
          console.log(json)
        })
        .catch(err => console.error(err));
      },
      nextSlice() {
        fetch("http://localhost:7000/cpu/rr", {
          "method": "POST"
        }).then(response => response.json())
          .then(json => {
            if (json.text) console.log(json.text)
            else {
              this.$store.commit('updateInfo',json)
              if (this.info.readyQueue)
                this.pcb = this.info.pcbs[this.info.readyQueue[0]]
            }
            console.log(json)
          })
          .catch(err => console.log(err));
        this.slice+=1;
      },
      autoSlice() {
        this.auto=true
        this.timer=setInterval(this.nextSlice, this.speed)
      },
      pauseSlice() {
        clearInterval(this.timer)
        this.auto=false
      },
      showPcb(key) {
        // if (this.info.readyQueue)
        //   this.pcb=this.info.pcbs[this.info.readyQueue[key]]
        this.pcb=this.info.pcbs[key]
      },
      getState(state) {
        switch(state) {
          case -1: return "终止态";
          case 0: return "新建态";
          case 1: return "就绪态";
          case 2: return "运行态";
          case 3: return "阻塞态";
        }
      },
      getColor(num) {
        return this.colors[num]
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
</style>
