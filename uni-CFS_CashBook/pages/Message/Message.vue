<template>
	<view class="message">
		<view class="status_bar"></view>
		<nav-bar @clickhome="toHome()" @clickadd="toAdd()"></nav-bar>
		<!-- 点击去往详细页面 -->
		<scroll-view  croll-y="true" :scroll-top="scrollTop" class="scrollY" >
			<view class="content"
			 style="background-color: #0000FF;" v-for="(item,index) in listData" :key="index">
					<uni-list class="lists" >
						<!-- 每个账户对应的资料信息 -->
						<uni-list-item  clickable  @click="onClick(item,index)"
						:title='"账户名："+item.accountName' 
						:note='"流动资金："+item.accountFreeMoney'>	
						</uni-list-item>
					</uni-list>
			</view>	
		</scroll-view>
		<tab-bar></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				listData: [], // 所有资金账户数据
				userId: '',	// 用户唯一值
				accountId: [], // 账户唯一值
			}
		},
		mounted(){
			// 获取所有资金账户数据
			// 获取本地缓存中的 userId
			uni.getStorage({
				key: 'userId',
				success: (res) => {
					this.userId = res.data
					// 通过 userId 获取所有资金账户数据
					uni.request({
						url: "http://localhost:8080/accounts",
						method: 'GET',
						data:{
							userId: this.userId
						},
						success: (res) => {
							this.listData = res.data
						},
						fail: (err) => {
							console.log("获取数据失败",err)
						}
					})
				
				}
			})
		},
		methods: {
			// 点击list-item事件
			onClick(item,index){
				uni.reLaunch({
					url: 'details/details?accountId='+item.accountId,
				})
			},
			// 删除该项
			switchChange(item,index){
				console.log("删除该项目");
			},
			
			// 跳转页面到 首页
			toHome(){
				uni.reLaunch({
					url: "../home/home"
				})
			},
			// 跳转页面到 添加数据
			toAdd(){
				uni.reLaunch({
					url: "../Add/Add"
				})
			}
		}
	}
</script>

<style>
.navBar{
	background-color: #0000FF;
	width: 500rpx;
	text-align: center;
}	
.navBarLeft{
	width: 250rpx;
	text-align: center;
	font-size: 30rpx;
}
.navBarTitle{
	width: 250rpx;
	font-size: 30rpx;
	text-align: center;
	background-color: #0000FF;
}
.navBarRight{
	width: 250rpx;
	text-align: center;
	font-size: 30rpx;
}
.content{
	height: auto;
	width: auto;
	position: relative;
}
.minSlip{
	height: 180rpx;
}
.lists{
	margin-top: 400rpx;
	height: 200rpx;
}
.scrollY{
	height: 900rpx;
}
</style>
