<template>
	<view class="home">
		<!-- 自定义导航栏高度 -->
		<view class="status_bar"></view>
		<nav-bar @clickadd="toAdd()" @clickmessage="toMessage()"></nav-bar>
		<view class="main">
			<view class="main-avatarUrl">
				<image :src="wxList.avatarUrl" mode=""></image>
				<view>
					<view class="main-nickName">
						{{ wxList.nickName }}
					</view>
				</view>
			</view>
			<view class="message">
				<rich-text>流动资金：{{ workingCapital }}</rich-text>
				<rich-text>总资产：{{ totalAssets }}</rich-text>
				<rich-text>总负债：{{ TotalLiabilities }}</rich-text>
			</view>
		</view>
		<tab-bar :current="0"></tab-bar>
	</view>
</template>

<script>
	export default {
		name:"home",
		data() {
			return {
				wxList:[], // 用户数据
				workingCapital: "0.00" ,// 流动资金
				totalAssets: "0.00",	// 总资产
				TotalLiabilities: "0.00" ,// 总负债
				userId: ''
			}
		},
		onLoad(params){
			console.log(params)
			this.wxList = params
			// console.log(this.wxList)
		},
		mounted(){
			// 获取本地缓存中的avatarUrl(微信头像)
			uni.getStorage({
				key: 'avatarUrl',
				success: (res) => {
					console.log("用户的头像",res.data)
					this.wxList = res.data
				}
			})
			// 获取本地缓存中的nickName(用户微信名)
			uni.getStorage({
				key: 'nickName',
				success: (res) => {
					console.log("用户的微信名",res.data)
					this.wxList = res.data
				},
				fail:(err) =>{
					// 跳转页面
					uni.reLaunch({
						url: "../login/login",
						success: (res) => {
							uni.showToast({
								title: '您还没登录',
								icon:'none'
							})
						},
						fail: (err) => {
							console.log("登录失败")
						}
					})
				}
			})
			// 获取本地缓存中的userId(用户唯一值)
			uni.getStorage({
				key: 'userId',
				success: (res) => {
					console.log("缓存数据",res.data)
					this.userId = res.data
					// 通过 userId 获取所有资金账户数据
					uni.request({
						url: 'http://localhost:8080/usermsg',
						method: 'GET',
						data: {
							userId: this.userId
						},
						success: (res) => {
							console.log("获取所有资金账户数据成功",res)
							this.workingCapital= res.data.freemoney,// 流动资金
							this.totalAssets= res.data.totalmoney,	// 总资产
							this.TotalLiabilities= res.data.debtmoney; // 总负债
							if(!this.TotalLiabilities) this.TotalLiabilities = 0.00;
						}
					})
				}
			})
		},
		methods: {
			toAdd(){
				uni.reLaunch({
					url:"../Add/Add",
					success: (res) => {
						console.log("跳转Add成功",res)
					},
					fail: (err) => {
						console.log("跳转Add失败",err)
					}
				})
			},
			toMessage(){
				uni.reLaunch({
					url: "../Message/Message",
					success: (res) => {
						console.log("跳转message成功",res)
					},
					fail: (err) => {
						console.log("跳转message失败",err)
					}
				})
			}
		}
	}
</script>

<style>
.home{
	width: 100%;
	height: 100%;
}
.navBarLeft{
	background-color: #0000FF;
	width: 250rpx;
	text-align: center;
	font-size: 30rpx;
}
.main{
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: column;
	margin-top: 150rpx;
	margin-left: 50rpx;
}
.main-avatarUrl>image{
	width: 200rpx;
	height: 200rpx;
	
}
.main-nickName{
	width: 200rpx;
	margin-top: 30rpx;
	text-align: center;
}

.message{
	width: 300rpx;
	height: 300rpx;
	position: absolute;
	right:180rpx;
}
</style>
