(function(t){function e(e){for(var n,o,s=e[0],l=e[1],c=e[2],d=0,v=[];d<s.length;d++)o=s[d],Object.prototype.hasOwnProperty.call(i,o)&&i[o]&&v.push(i[o][0]),i[o]=0;for(n in l)Object.prototype.hasOwnProperty.call(l,n)&&(t[n]=l[n]);u&&u(e);while(v.length)v.shift()();return r.push.apply(r,c||[]),a()}function a(){for(var t,e=0;e<r.length;e++){for(var a=r[e],n=!0,s=1;s<a.length;s++){var l=a[s];0!==i[l]&&(n=!1)}n&&(r.splice(e--,1),t=o(o.s=a[0]))}return t}var n={},i={app:0},r=[];function o(e){if(n[e])return n[e].exports;var a=n[e]={i:e,l:!1,exports:{}};return t[e].call(a.exports,a,a.exports,o),a.l=!0,a.exports}o.m=t,o.c=n,o.d=function(t,e,a){o.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:a})},o.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},o.t=function(t,e){if(1&e&&(t=o(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var a=Object.create(null);if(o.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var n in t)o.d(a,n,function(e){return t[e]}.bind(null,n));return a},o.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return o.d(e,"a",e),e},o.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},o.p="/";var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=e,s=s.slice();for(var c=0;c<s.length;c++)e(s[c]);var u=l;r.push([0,"chunk-vendors"]),a()})({0:function(t,e,a){t.exports=a("56d7")},3303:function(t,e,a){"use strict";a("d528")},"4d49":function(t,e,a){},"56d7":function(t,e,a){"use strict";a.r(e);a("e260"),a("e6cf"),a("cca6"),a("a79d");var n=a("2b0e"),i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-app",[a("v-navigation-drawer",{attrs:{permanent:"",app:"",width:"14rem"}},[a("v-list-item",{staticClass:"grey lighten-4"},[a("v-list-item-content",[a("v-list-item-title",{staticClass:"text-h4 font-weight-black"},[t._v(" 操作系统 ")]),a("v-list-item-subtitle",[t._v(" 课程设计 ")])],1)],1),a("v-divider"),a("v-list",{attrs:{nav:""}},t._l(t.items,(function(e){return a("v-list-item",{key:e.title,attrs:{link:"",to:e.url}},[a("v-list-item-icon",[a("v-icon",{attrs:{large:"",color:"indigo"}},[t._v(t._s(e.icon))])],1),a("v-list-item-content",[a("v-list-item-title",[t._v(" "+t._s(e.title)+" ")])],1)],1)})),1)],1),a("v-main",[a("router-view")],1)],1)},r=[],o={name:"App",data:function(){return{items:[{title:"文件管理",icon:"mdi-folder-home",url:"/"},{title:"调度管理",icon:"mdi-cpu-64-bit",url:"/cpu"},{title:"关于",icon:"mdi-information",url:"/about"}]}},computed:{snackbar:function(){return this.$store.snackbar},hint:function(){return this.$store.hint}}},s=o,l=a("2877"),c=a("6544"),u=a.n(c),d=a("7496"),v=a("ce7e"),h=a("132d"),f=a("8860"),p=a("da13"),m=a("5d23"),b=a("34c3"),_=a("f6c4"),x=a("f774"),g=Object(l["a"])(s,i,r,!1,null,null,null),k=g.exports;u()(g,{VApp:d["a"],VDivider:v["a"],VIcon:h["a"],VList:f["a"],VListItem:p["a"],VListItemContent:m["a"],VListItemIcon:b["a"],VListItemSubtitle:m["b"],VListItemTitle:m["c"],VMain:_["a"],VNavigationDrawer:x["a"]});var C=a("8c4f"),y=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-container",{staticClass:"grey lighten-3",attrs:{fluid:"","fill-height":""}},[a("v-card",{staticClass:"rounded-xl mx-auto",attrs:{width:"100%",height:"42rem",elevation:"10"}},[a("v-row",[a("v-col",[a("v-card",{staticClass:"rounded-t-xl indigo white--text text-h5",attrs:{flat:"",tile:""}},[a("toolbar",{attrs:{itemi:t.active,snackbar:t.snackbar,hint:t.hint},on:{"update:snackbar":function(e){t.snackbar=e},"update:hint":function(e){t.hint=e}}})],1)],1)],1),a("v-row",{attrs:{"no-gutters":""}},[a("v-col",{attrs:{cols:"3"}},[a("file-tree",{attrs:{active:t.active},on:{"update:active":function(e){t.active=e}}})],1),a("v-divider",{attrs:{vertical:""}}),a("v-col",[a("file-info",{attrs:{item:t.active}})],1),a("v-divider",{attrs:{vertical:""}}),a("v-col",[a("file-disk")],1)],1)],1),a("v-snackbar",{attrs:{timeout:6e3},scopedSlots:t._u([{key:"action",fn:function(e){var n=e.attrs;return[a("v-btn",t._b({attrs:{color:"blue",text:""},on:{click:function(e){t.snackbar=!1}}},"v-btn",n,!1),[t._v(" 关闭 ")])]}}]),model:{value:t.snackbar,callback:function(e){t.snackbar=e},expression:"snackbar"}},[a("span",{staticClass:"text-h5 text-wrapper"},[t._v(t._s(t.hint))])])],1)},w=[],V=(a("d3b7"),function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-card",{staticClass:"indigo lighten-5 rounded-bl-xl overflow-y-auto",attrs:{flat:"",tile:"",width:"100%",height:"38rem"}},[a("v-treeview",{attrs:{items:t.items,active:t.active,activatable:"","item-key":"path",rounded:"",hoverable:"","return-object":!0},on:{"update:active":[function(e){t.active=e},t.emitActive]},scopedSlots:t._u([{key:"prepend",fn:function(e){var n=e.item,i=e.open;return["dir"===n.type?a("v-icon",{attrs:{color:"blue"}},[t._v(" "+t._s(i?"mdi-folder-open":"mdi-folder")+" ")]):a("v-icon",{attrs:{color:"green"}},[t._v(" "+t._s(t.files[n.type])+" ")])]}}]),model:{value:t.tree,callback:function(e){t.tree=e},expression:"tree"}})],1)}),S=[],D={props:[],data:function(){return{files:{txt:"mdi-file-document-outline",exe:"mdi-rocket"},active:[],tree:[]}},computed:{items:function(){return this.$store.state.fileItems}},methods:{emitActive:function(){this.$emit("update:active",this.active[0])}}},T=D,F=a("b0af"),I=a("eb2a"),$=Object(l["a"])(T,V,S,!1,null,null,null),j=$.exports;u()($,{VCard:F["a"],VIcon:h["a"],VTreeview:I["a"]});var O=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-toolbar",{attrs:{flat:"",color:"rgba(0, 0, 0, 0)"}},[t.item?a("v-container",{attrs:{fluid:""}},["dir"===t.item.type?a("v-dialog",{attrs:{persistent:"","max-width":"600px"},scopedSlots:t._u([{key:"activator",fn:function(e){var n=e.on,i=e.attrs;return[a("v-btn",t._g(t._b({staticClass:"primary text-h6 ml-2",attrs:{dark:""}},"v-btn",i,!1),n),[t._v(" 新建目录 ")])]}}],null,!1,3738651944),model:{value:t.createDirDialog,callback:function(e){t.createDirDialog=e},expression:"createDirDialog"}},[a("v-card",[a("v-card-title",[a("span",{staticClass:"text-h5"},[t._v("新建目录")])]),a("v-card-text",[a("v-text-field",{attrs:{label:"请输入目录名",hint:"不超过3个的字母或数字组合，如d1、dd",maxlength:"3",rules:[function(t){return t.length<=3||"最多3个字母或数字组合"}],required:""},model:{value:t.dirName,callback:function(e){t.dirName=e},expression:"dirName"}})],1),a("v-card-actions",[a("v-spacer"),a("v-btn",{attrs:{color:"red darken-1",text:""},on:{click:function(e){t.createDirDialog=!1}}},[t._v(" 取消 ")]),a("v-btn",{attrs:{color:"blue darken-1",text:""},on:{click:t.createDir}},[t._v(" 确认 ")])],1)],1)],1):t._e(),"dir"===t.item.type?a("v-dialog",{attrs:{persistent:"","max-width":"600px"},scopedSlots:t._u([{key:"activator",fn:function(e){var n=e.on,i=e.attrs;return[a("v-btn",t._g(t._b({staticClass:"primary ml-3 text-h6",attrs:{dark:""}},"v-btn",i,!1),n),[t._v(" 新建文件 ")])]}}],null,!1,3866168259),model:{value:t.createFileDialog,callback:function(e){t.createFileDialog=e},expression:"createFileDialog"}},[a("v-card",[a("v-card-title",[a("span",{staticClass:"text-h5"},[t._v("新建文件")])]),a("v-card-text",[a("v-form",[a("v-text-field",{attrs:{label:"请输入文件名",hint:"不超过3个的字母或数字组合，如f01、f02",maxlength:"3",rules:[function(t){return t.length<=3||"最多3个字母或数字组合"}],required:""},model:{value:t.fileName,callback:function(e){t.fileName=e},expression:"fileName"}}),a("v-select",{attrs:{items:[{text:"txt文本文件",value:"txt"},{text:"exe可执行文件",value:"exe"}],"item-text":"text","item-value":"value",rules:[function(t){return!!t||"Item is required"}],label:"文件类型",required:""},model:{value:t.fileType,callback:function(e){t.fileType=e},expression:"fileType"}}),a("v-textarea",{attrs:{filled:"",label:"文件内容","auto-grow":"",rule:[function(t){return 0===t.length||"请输入文件内容"}],required:""},model:{value:t.fileContent,callback:function(e){t.fileContent=e},expression:"fileContent"}})],1)],1),a("v-card-actions",[a("v-spacer"),a("v-btn",{attrs:{color:"red darken-1",text:""},on:{click:function(e){t.createFileDialog=!1}}},[t._v(" 取消 ")]),a("v-btn",{attrs:{color:"blue darken-1",text:""},on:{click:t.createFile}},[t._v(" 确认 ")])],1)],1)],1):t._e(),t.item?a("v-dialog",{attrs:{persistent:"","max-width":"600px"},scopedSlots:t._u([{key:"activator",fn:function(e){var n=e.on,i=e.attrs;return[a("v-btn",t._g(t._b({staticClass:"red ml-3 text-h6",attrs:{dark:""}},"v-btn",i,!1),n),[t._v(" 删除 ")])]}}],null,!1,1612560419),model:{value:t.deleteDialog,callback:function(e){t.deleteDialog=e},expression:"deleteDialog"}},[a("v-card",[a("v-card-title",[a("span",{staticClass:"text-h5"},[t._v("删除")])]),a("v-card-text",[a("span",{staticClass:"text-h5"},[t._v("确认删除 "+t._s(t.item.name)+" ?")])]),a("v-card-actions",[a("v-spacer"),a("v-btn",{attrs:{color:"red darken-1",text:""},on:{click:function(e){t.deleteDialog=!1}}},[t._v(" 取消 ")]),a("v-btn",{attrs:{color:"blue darken-1",text:""},on:{click:t.deleteTarget}},[t._v(" 确认 ")])],1)],1)],1):t._e(),"exe"===t.item.type?a("v-btn",{staticClass:"primary ml-3 text-h6",on:{click:t.execute}},[t._v(" 执行文件 ")]):t._e()],1):a("v-container",{attrs:{fluid:""}},[a("span",{staticClass:"white--text"},[t._v("请选择一个文件或目录（ / 是根目录）")])])],1)},P=[],N={props:["itemi"],data:function(){return{dirName:"",createDirDialog:!1,fileName:"",fileType:"txt",fileContent:"",createFileDialog:!1,deleteDialog:!1,item:null}},watch:{itemi:function(t){this.item=t}},methods:{createDir:function(){if(this.dirName&&this.dirName.length<=3){var t=new FormData;t.append("path",this.getParentPath()+this.dirName),this.doFetch("http://localhost:7000/file/create/dir",t)}this.createDirDialog=!1},createFile:function(){if(this.fileName&&this.fileType&&this.fileContent){var t=new FormData;t.append("path",this.getParentPath()+this.fileName+"."+this.fileType),t.append("content",this.fileContent),this.doFetch("http://localhost:7000/file/create/file",t)}else this.$emit("update:snackbar",!0),this.$emit("update:hint","文件名或文件内容为空，不能新建文件！");this.createFileDialog=!1},deleteTarget:function(){var t=new FormData;t.append("path",this.item.path),this.doFetch("http://localhost:7000/file/delete",t),this.deleteDialog=!1,this.item=null},getParentPath:function(){return"/"===this.item.path?"/":this.item.path+"/"},doFetch:function(t,e){var a=this;fetch(t,{method:"POST",mode:"cors",body:e}).then((function(t){return t.json()})).then((function(t){t.error?(console.log(t.error),a.$emit("update:snackbar",!0),a.$emit("update:hint",t.error)):(a.$store.dispatch("updateFileItems",t),a.$emit("update:snackbar",!0),a.$emit("update:hint","操作成功，目录树已刷新！"))})).catch((function(t){return console.error(t)}))},execute:function(){var t=this;console.log(this.item.content);var e=new FormData;e.append("content",this.item.content),fetch("http://localhost:7000/cpu/execute",{method:"POST",mode:"cors",body:e}).then((function(t){return t.json()})).then((function(e){e.text?(t.$emit("update:hint",e.text),t.$emit("update:snackbar",!0)):(t.$store.commit("updateInfo",e),t.$emit("update:hint","创建进程 "+e.readyQueue[e.readyQueue.length-1]+" 成功！"),t.$emit("update:snackbar",!0))})).catch((function(t){return console.log(t)}))}}},B=N,Q=a("8336"),E=a("99d9"),A=a("a523"),M=a("169a"),L=a("4bd4"),R=a("b974"),q=a("2fa4"),G=a("8654"),H=a("a844"),J=a("71d9"),z=Object(l["a"])(B,O,P,!1,null,null,null),K=z.exports;u()(z,{VBtn:Q["a"],VCard:F["a"],VCardActions:E["a"],VCardText:E["b"],VCardTitle:E["c"],VContainer:A["a"],VDialog:M["a"],VForm:L["a"],VSelect:R["a"],VSpacer:q["a"],VTextField:G["a"],VTextarea:H["a"],VToolbar:J["a"]});var U=function(){var t=this,e=t.$createElement,a=t._self._c||e;return t.item?a("v-container",[a("v-row",[a("v-col",{attrs:{cols:"12"}},[a("v-card",{attrs:{height:"6rem"}},[a("v-simple-table",{scopedSlots:t._u([{key:"default",fn:function(){return[a("thead",[a("tr",[a("th",{staticClass:"text-left"},[t._v(" 名称 ")]),a("th",{staticClass:"text-left"},[t._v(" 路径 ")]),a("th",{staticClass:"text-left"},[t._v(" 类型 ")]),a("th",{staticClass:"text-left"},[t._v(" 起始磁盘块 ")])])]),a("tbody",[a("tr",[a("td",[t._v(t._s(t.item.name))]),a("td",[t._v(t._s(t.item.path))]),a("td",{staticClass:"text-wrapper"},[t._v(t._s(t.getType(t.item.type)))]),a("td",[t._v(t._s(t.item.startDiskBlockNum))])])])]},proxy:!0}],null,!1,1997871372)})],1)],1),"txt"===t.item.type||"exe"===t.item.type?a("v-col",{attrs:{cols:"12"}},[a("v-card",{attrs:{height:"29rem"}},[a("v-card-title",[a("v-btn",{staticClass:"indigo",attrs:{dark:""},on:{click:t.saveFile}},[t._v(" 保存文件 ")]),a("span",{staticClass:"ml-8"},[t._v("字数："+t._s(t.text.length+"/128"))])],1),a("v-card-text",[a("v-textarea",{attrs:{filled:"","auto-grow":"",outlined:"",label:"编辑文件",height:"24rem",maxlength:"128",rules:[function(t){return t.length<=128||"最多输入128个字符"}]},model:{value:t.text,callback:function(e){t.text=e},expression:"text"}})],1)],1)],1):a("v-col",[a("v-card",{attrs:{height:"29rem"}},[a("v-card-text",[a("span",{staticClass:"text-h5"},[t._v("请选择一个文件以编辑")])])],1)],1)],1)],1):t._e()},W=[],X={props:["item"],data:function(){return{}},computed:{text:{get:function(){return null===this.item?"":this.item.content},set:function(t){this.item.content=t}}},methods:{saveFile:function(){var t=this;if(this.text&&this.item.path){var e=new FormData;e.append("content",this.text),e.append("path",this.item.path),fetch("http://localhost:7000/file/modify",{method:"POST",mode:"cors",body:e}).then((function(t){return t.json()})).then((function(e){e.error?console.log(e.error):t.$store.dispatch("updateFileItems",e)})).catch((function(t){return console.error(t)}))}},getType:function(t){switch(t){case"dir":return"目录";case"txt":return"文本\n文件";case"exe":return"可执行\n文件"}}}},Y=X,Z=(a("7b96"),a("62ad")),tt=a("0fd9"),et=a("1f4f"),at=Object(l["a"])(Y,U,W,!1,null,"9cbc0dd2",null),nt=at.exports;u()(at,{VBtn:Q["a"],VCard:F["a"],VCardText:E["b"],VCardTitle:E["c"],VCol:Z["a"],VContainer:A["a"],VRow:tt["a"],VSimpleTable:et["a"],VTextarea:H["a"]});var it=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-container",{staticClass:"fill-height"},[a("v-card",{staticClass:"mx-auto"},[a("v-card-title",[t._v(" 磁盘块 ")]),a("table",[a("tbody",t._l(8,(function(e){return a("tr",{key:e},t._l(16,(function(n){return a("td",{key:n},[a("v-hover",{scopedSlots:t._u([{key:"default",fn:function(i){var r=i.hover;return[a("v-card",{class:{diskBlock:!0,orange:!!t.fat[16*(e-1)+n-1]&&t.fat[16*(e-1)+n-1].used},attrs:{elevation:r?24:0,tile:"",height:"2.5rem"},on:{click:function(a){return t.updateEvent(e,n)}}},[t._v(" "+t._s(16*(e-1)+n-1)+" ")])]}}],null,!0)})],1)})),0)})),0)])],1),a("v-card",{staticClass:"mx-auto"},[a("span",[t._v("磁盘块 "+t._s(t.select)+" 的下一磁盘块："+t._s(t.nextBlock))])]),t.block?a("v-card",{staticClass:"mx-auto"},[a("v-card-title",[t._v(" 磁盘块 "+t._s(t.select)+" 内容 ")]),a("table",[a("tbody",t._l(4,(function(e){return a("tr",{key:e},t._l(16,(function(n){return a("td",{key:n},[a("v-hover",{scopedSlots:t._u([{key:"default",fn:function(i){var r=i.hover;return[a("v-card",{staticClass:"diskBlock",attrs:{elevation:r?24:0,tile:"",width:"2rem"}},[t._v(" "+t._s(t.block[16*(e-1)+n-1])+" ")])]}}],null,!0)})],1)})),0)})),0)])],1):a("v-card",[a("span",[t._v("请选择一个磁盘块以查看其内容")])])],1)},rt=[],ot={data:function(){return{select:0,nextBlock:0,block:null}},computed:{fat:function(){return this.$store.state.fat}},watch:{fat:function(t){var e=this;this.nextBlock=t[this.select].next;var a=new FormData;a.append("num",this.select),fetch("http://localhost:7000/file/diskblock",{method:"POST",mode:"cors",body:a}).then((function(t){return t.json()})).then((function(t){t.error?console.log(t.error):e.block=t})).catch((function(t){return console.error(t)}))}},methods:{updateEvent:function(t,e){var a=this;this.select=16*(t-1)+e-1,this.nextBlock=this.fat[this.select].next;var n=new FormData;n.append("num",this.select),fetch("http://localhost:7000/file/diskblock",{method:"POST",mode:"cors",body:n}).then((function(t){return t.json()})).then((function(t){t.error?console.log(t.error):a.block=t})).catch((function(t){return console.error(t)}))}}},st=ot,lt=(a("bb1a"),a("ce87")),ct=Object(l["a"])(st,it,rt,!1,null,null,null),ut=ct.exports;u()(ct,{VCard:F["a"],VCardTitle:E["c"],VContainer:A["a"],VHover:lt["a"]});var dt={components:{"file-tree":j,toolbar:K,"file-info":nt,"file-disk":ut},mounted:function(){this.getFileItems(),setTimeout((function(){var t=document.getElementsByClassName("v-treeview-node__toggle")[0];t.click()}),500)},data:function(){return{fileItems:[],active:null,snackbar:!1,hint:"hello"}},methods:{getFileItems:function(){var t=this;fetch("http://localhost:7000/file/check",{method:"GET",headers:{}}).then((function(t){return t.json()})).then((function(e){t.$store.dispatch("updateFileItems",e),console.log(t.fileItems)})).catch((function(t){console.error(t)}))}}},vt=dt,ht=(a("dc4e"),a("2db4")),ft=Object(l["a"])(vt,y,w,!1,null,null,null),pt=ft.exports;u()(ft,{VBtn:Q["a"],VCard:F["a"],VCol:Z["a"],VContainer:A["a"],VDivider:v["a"],VRow:tt["a"],VSnackbar:ht["a"]});var mt,bt,_t=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-container",{staticClass:"grey lighten-3",attrs:{fluid:"","fill-height":""}},[a("v-card",{staticClass:"rounded-xl mx-auto",attrs:{width:"100%",height:"72rem",elevation:"5"}},[a("v-container",{staticClass:"text-center",attrs:{"fill-height":"",fluid:""}},[a("v-row",[a("v-col",[a("v-row",[a("v-col",{attrs:{cols:"12"}},[a("v-card",{staticClass:"teal lighten-5 fill-height",attrs:{height:"19rem"}},[t.auto?a("div",[a("v-card-text",[a("v-btn",{staticClass:"indigo",attrs:{dark:""},on:{click:t.pauseSlice}},[t._v(" 暂停 ")])],1)],1):a("div",[a("v-card-text",[a("v-btn",{staticClass:"indigo",attrs:{dark:""},on:{click:t.nextSlice}},[t._v("手动执行下一个时间片")])],1),a("v-card-text",[a("v-btn",{staticClass:"indigo",attrs:{dark:""},on:{click:t.autoSlice}},[t._v(" 自动执行时间片 ")]),a("v-select",{attrs:{items:[{text:"时间片：快 500ms",value:500},{text:"时间片：中 1000ms",value:1e3},{text:"时间片：慢 1500ms",value:1500}],"item-text":"text","item-value":"value"},model:{value:t.speed,callback:function(e){t.speed=e},expression:"speed"}})],1)],1),a("v-card-text",{staticClass:"black--text"},[a("span",{staticClass:"text-h5"},[t._v("第 ")]),a("span",{staticClass:"text-h3 red--text"},[t._v(t._s(t.slice))]),a("span",{staticClass:"text-h5"},[t._v(" 时间片")])])],1)],1),a("v-col",{staticClass:"text-center",attrs:{cols:"12"}},[a("v-card",{staticClass:"orange lighten-5 fill-height",attrs:{height:"19rem"}},[t.info?a("v-card-text",[a("v-progress-circular",{attrs:{size:200,width:30,value:t.info.occupyMemory/512*100,color:"primary"}},[a("div",{staticClass:"text-h6"},[t._v(" "+t._s(t.info.occupyMemory)+"/"+t._s(512)+"字节 ")])])],1):t._e(),a("v-card-text",[a("div",{staticClass:"text-h5 black--text"},[t._v("内存占用率")])])],1)],1)],1)],1),a("v-col",[t.pcb?a("v-card",{staticClass:"green lighten-5 fill-height"},[a("v-card-title",[t._v(" 进程 "),a("span",{staticClass:"text-h3 mx-1 red--text"},[t._v("#"+t._s(t.pcb.pid))]),t._v(" 信息 ")]),a("div",{staticClass:"pt-2"},[a("v-simple-table",{scopedSlots:t._u([{key:"default",fn:function(){return[a("thead",[a("tr",[a("th",[t._v("x寄存器")]),a("th",[t._v("指令计数器")])])]),a("tbody",[a("tr",{directives:[{name:"ripple",rawName:"v-ripple"}]},[a("td",{staticClass:"text-h6"},[t._v(t._s(t.pcb.ax))]),a("td",{staticClass:"text-h6"},[t._v(t._s(t.pcb.pc))])])])]},proxy:!0}],null,!1,121899825)})],1),a("div",{staticClass:"pt-2"},[a("v-simple-table",{attrs:{"fixed-header":""},scopedSlots:t._u([{key:"default",fn:function(){return[a("thead",[a("tr",[a("th",[t._v("行号")]),a("th",[t._v("指令")])])]),a("tbody",t._l(t.pcb.instructions.length,(function(e){return a("tr",{directives:[{name:"ripple",rawName:"v-ripple"}],key:e,class:{"purple lighten-3":e-1===t.pcb.pc}},[a("td",{staticClass:"text-h6"},[t._v(t._s(e-1))]),a("td",{staticClass:"text-h5"},[t._v(t._s(t.pcb.instructions[e-1]))])])})),0)]},proxy:!0}],null,!1,2707862533)})],1)],1):a("v-card",{staticClass:"green lighten-5 fill-height"},[a("v-card-text",[a("div",{staticClass:"text-h5"},[t._v("在进程队列"),a("br"),t._v("选择一个进程"),a("br"),t._v("以查看信息")])])],1)],1),a("v-col",[a("v-card",{staticClass:"green lighten-5 fill-height"},[a("v-card-title",[t._v(" 进程队列 ")]),a("v-simple-table",{attrs:{"fixed-header":""},scopedSlots:t._u([{key:"default",fn:function(){return[a("thead",[a("tr",[a("td",[t._v("进程编号")]),a("td",[t._v("状态")])])]),t.info?a("tbody",t._l(t.info.readyQueue,(function(e){return a("tr",{directives:[{name:"ripple",rawName:"v-ripple"}],key:e,class:{"orange lighten-3":e===t.info.readyQueue[0]||-1===t.info.pcbs[e].processState},on:{click:function(a){return t.showPcb(e)}}},[a("td",{staticClass:"text-h5"},[t._v("#"+t._s(e))]),a("td",{staticClass:"text-h5"},[t._v(t._s(t.getState(t.info.pcbs[e].processState)))])])})),0):t._e()]},proxy:!0}])})],1)],1),a("v-col",[a("v-row",[a("v-col",{attrs:{cols:"12"}},[a("v-card",{staticClass:"green lighten-5 fill-height"},[a("v-card-title",[t._v(" 阻塞队列 ")]),a("v-simple-table",{attrs:{dense:"","fixed-header":"",height:"14rem"},scopedSlots:t._u([{key:"default",fn:function(){return[a("thead",[a("tr",[a("th",[t._v("序号")]),a("th",[t._v("进程编号")])])]),t.info&&t.info.blockedQueue?a("tbody",t._l(t.info.blockedQueue,(function(e){return a("tr",{directives:[{name:"ripple",rawName:"v-ripple"}],key:e,on:{click:function(a){t.pcb=t.info.pcbs[e]}}},[a("td",[t._v("#"+t._s(e))]),a("td",[t._v(t._s(t.getState(t.info.pcbs[e].processState)))])])})),0):t._e()]},proxy:!0}])})],1)],1),a("v-col",{attrs:{cols:"12"}},[a("v-card",{staticClass:"blue lighten-4 fill-height",attrs:{height:"21rem"}},[a("v-card-title",[t._v(" 设备占用 ")]),a("v-simple-table",{attrs:{dense:"","fixed-header":"",height:"14rem"},scopedSlots:t._u([{key:"default",fn:function(){return[a("thead",[a("tr",[a("th",[t._v("序号")]),a("th",[t._v("使用中的进程编号")])])]),t.info&&t.info.blockedQueue?a("tbody",[a("tr",{attrs:{height:"50rem"}},[a("td",{staticClass:"text-h5"},[t._v("设备A")]),a("td",t._l(t.info.deviceAQueue.length,(function(e){return a("v-chip",{key:e,staticClass:"red lighten-4"},[t._v(" #"+t._s(t.info.deviceAQueue[e-1])+" ")])})),1)]),a("tr",{attrs:{height:"50rem"}},[a("td",{staticClass:"text-h5"},[t._v("设备B")]),a("td",t._l(t.info.deviceBQueue.length,(function(e){return a("v-chip",{key:e,staticClass:"red lighten-4"},[t._v(" #"+t._s(t.info.deviceBQueue[e-1])+" ")])})),1)]),a("tr",{attrs:{height:"50rem"}},[a("td",{staticClass:"text-h5"},[t._v("设备C")]),a("td",t._l(t.info.deviceCQueue.length,(function(e){return a("v-chip",{key:e,staticClass:"red lighten-4"},[t._v(" #"+t._s(t.info.deviceCQueue[e-1])+" ")])})),1)])]):t._e()]},proxy:!0}])})],1)],1)],1)],1)],1),a("v-row",[a("v-col",[a("v-card",{staticClass:"teal lighten-3"},[a("v-card-title",{staticClass:"teal lighten-4"},[t._v(" 内存单元（共512字节） ")]),a("v-card-text",{staticClass:"mt-2"},[a("table",[a("tbody",t._l(16,(function(e){return a("tr",{key:e},t._l(32,(function(n){return a("td",{key:n},[a("v-hover",{scopedSlots:t._u([{key:"default",fn:function(i){var r=i.hover;return[t.info?a("v-card",{class:["diskBlock",t.getColor(t.info.mem[32*(e-1)+n-1].num)],attrs:{elevation:r?24:0,tile:"",width:"2.4rem"}},[t._v(" "+t._s(32*(e-1)+n-1)+" ")]):t._e()]}}],null,!0)})],1)})),0)})),0)])])],1)],1)],1)],1)],1)],1)},xt=[],gt=(a("fb6a"),{components:{},mounted:function(){this.getInfo()},data:function(){return{pcb:null,colors:["orange","green","blue","light-green","purple","light-blue","purple","pink","lime","yellow","white"],timer:0,slice:0,speed:500,auto:!1}},computed:{info:function(){return this.$store.state.info}},methods:{getRandomInt:function(){return Math.floor(46*Math.random()+5)},getInfo:function(){var t=this;fetch("http://localhost:7000/cpu/getinfo",{method:"GET"}).then((function(t){return t.json()})).then((function(e){e.text?console.log(e.text):t.$store.commit("updateInfo",e),console.log(e)})).catch((function(t){return console.error(t)}))},nextSlice:function(){var t=this;fetch("http://localhost:7000/cpu/rr",{method:"POST"}).then((function(t){return t.json()})).then((function(e){e.text?console.log(e.text):(t.$store.commit("updateInfo",e),t.info.readyQueue&&(t.pcb=t.info.pcbs[t.info.readyQueue[0]])),console.log(e)})).catch((function(t){return console.log(t)})),this.slice+=1},autoSlice:function(){this.auto=!0,this.timer=setInterval(this.nextSlice,this.speed)},pauseSlice:function(){clearInterval(this.timer),this.auto=!1},showPcb:function(t){this.pcb=this.info.pcbs[t]},getState:function(t){switch(t){case-1:return"终止态";case 0:return"新建态";case 1:return"就绪态";case 2:return"运行态";case 3:return"阻塞态"}},getColor:function(t){return this.colors[t]}}}),kt=gt,Ct=(a("3303"),a("cc20")),yt=a("490a"),wt=a("269a"),Vt=a.n(wt),St=a("5607"),Dt=Object(l["a"])(kt,_t,xt,!1,null,null,null),Tt=Dt.exports;u()(Dt,{VBtn:Q["a"],VCard:F["a"],VCardText:E["b"],VCardTitle:E["c"],VChip:Ct["a"],VCol:Z["a"],VContainer:A["a"],VHover:lt["a"],VProgressCircular:yt["a"],VRow:tt["a"],VSelect:R["a"],VSimpleTable:et["a"]}),Vt()(Dt,{Ripple:St["a"]});var Ft={},It=Object(l["a"])(Ft,mt,bt,!1,null,null,null),$t=It.exports,jt=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-container",{staticClass:"grey lighten-3",attrs:{fluid:"","fill-height":""}},[n("v-row",{staticClass:"text-center"},[n("v-col",{attrs:{cols:"12"}},[n("v-img",{staticClass:"my-3",attrs:{src:a("7f01"),contain:"",height:"200"}})],1),n("v-col",{staticClass:"mb-2"},[n("h1",{staticClass:"display-2 font-weight-bold mb-3"},[t._v(" 2019级 操作系统 "),n("span",{staticClass:"indigo--text"},[t._v("课程设计")])]),n("h2",[t._v(" 2021年11月1日 ")]),n("br"),n("h2",[t._v(" 谢育浩 黄梓豪 宋雨倩 陈琳 黄俊鸿 支永健 ")])])],1)],1)},Ot=[],Pt={data:function(){return{mates:[{id:"201925220524",name:"谢育浩"},{id:"201925220508",name:"黄梓豪"},{id:"201919210118",name:"宋雨倩"},{id:"201925220603",name:"陈琳"},{id:"201925220613",name:"黄俊鸿"},{id:"201925220629",name:"支永健"}]}}},Nt=Pt,Bt=a("adda"),Qt=Object(l["a"])(Nt,jt,Ot,!1,null,null,null),Et=Qt.exports;u()(Qt,{VCol:Z["a"],VContainer:A["a"],VImg:Bt["a"],VRow:tt["a"]}),n["a"].use(C["a"]);var At=[{path:"/",name:"文件管理",component:pt},{path:"/disk",name:"文件管理",redirect:"/"},{path:"/cpu",name:"调度管理",component:Tt},{path:"/help",name:"帮助",component:$t},{path:"/about",name:"About",component:Et}],Mt=new C["a"]({routes:At}),Lt=Mt,Rt=a("2f62");n["a"].use(Rt["a"]);var qt=new Rt["a"].Store({state:{fileItems:[],fat:[],info:null},mutations:{updateFileItems:function(t,e){t.fileItems=[e]},updateFat:function(t,e){t.fat=e.fat},updateInfo:function(t,e){t.info=e}},actions:{updateFileItems:function(t,e){t.commit("updateFileItems",e),fetch("http://localhost:7000/file/disk",{method:"GET",mode:"cors"}).then((function(t){return t.json()})).then((function(e){e.error?console.log(e.error):t.commit("updateFat",{fat:e})})).catch((function(t){return console.error(t)}))}},modules:{}}),Gt=(a("5363"),a("f309"));n["a"].use(Gt["a"]);var Ht=new Gt["a"]({icons:{iconfont:"mdi"}});n["a"].config.productionTip=!1,new n["a"]({router:Lt,store:qt,vuetify:Ht,render:function(t){return t(k)}}).$mount("#app")},6921:function(t,e,a){},"7b96":function(t,e,a){"use strict";a("8380")},"7f01":function(t,e,a){t.exports=a.p+"img/logo.9eaca10f.svg"},8380:function(t,e,a){},bb1a:function(t,e,a){"use strict";a("4d49")},d528:function(t,e,a){},dc4e:function(t,e,a){"use strict";a("6921")}});
//# sourceMappingURL=app.80769c54.js.map