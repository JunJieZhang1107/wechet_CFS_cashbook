<template>
	<view>
		<view class="status_bar"></view>
		<nav-bar @clickhome="toHome()" @clickadd="toAdd()" @clickmessage="toMessage"></nav-bar>
		<view class="details">
			<view class="side">
				<view class="bgColor">
					<text>详情信息</text>
				</view>
				<view @click="toTransfer()">
					<text>资金账户转移</text>
				</view>
			</view>
		</view>
		<view class="content">
			<uni-notice-bar :scrollable="true" :single="true" text="仅流动资金可以更新!" />
			<uni-grid :column="2" style="text-align: center;" :showBorder="false"  @change="gridChange">
				<uni-grid-item :index="1">
				    <text class="text">负债</text>
					<text class="text">{{liabilities}}</text>
				</uni-grid-item>
				<uni-grid-item  :index="2">
				    <text class="text">流动资金</text>
					<input v-model = "workingCapital" value=""  />
				</uni-grid-item>
				<uni-grid-item  :index="3">
				    <text class="text">投资</text>
					<text class="text">{{investment}}</text>
				</uni-grid-item>
				<uni-grid-item  :index="4">
				    <text class="text">外借</text>
					<text class="text">{{lend}}</text>
				</uni-grid-item>
			</uni-grid>
			<button @click="updateMsg">更新数据</button>
			<button @click="removeList">删除账号</button>
		</view>
		<tab-bar :current="0"></tab-bar>
	</view>
</template>

<script>
	export default {
		onLoad:function(option){
			// 通过message组件获取到accountId到detail中
			this.accountId = option.accountId;
			uni.request({
				url: 'http://localhost:8080/accountDetail',
				method: 'GET',
				data:{
					accountId : option.accountId
				},
				success: (res) => {
					this.liabilities=res.data.liabilities;// 负债
					this.workingCapital=res.data.workingCapital, // 流动资金
					this.investment= res.data.investment ,// 投资
					this.lend= res.data.lend	// 外借
				}
			})
			
		},   
		data() {
			return {
				liabilities:'0.00', // 负债
				workingCapital: '0.00', // 流动资金
				investment: '0.00' ,// 投资
				lend: '0.00'	,// 外借
				accountId :0,
				}
		},
		methods: {
			// 更新数据
			updateMsg(){
				uni.request({
					url: 'http://localhost:8080/account',
					method: 'PUT',
					data:{
						accountId:this.accountId,
						accountFreeMoney:this.workingCapital
					},
					success: (res) => {
						if(res.data.msg == "账户更新成功"){
							uni.reLaunch({
								url: '../Message'
							})
						}else{
							uni.showToast({
								title: '账户更新失败',
								icon:'none'
							})
						}
					}
				})
			},
			// 删除数据
			removeList(){
				// 通过 accountId 删除数据
				uni.request({
					url: 'http://localhost:8080/account/'+this.accountId,
					method: 'DELETE',
					success: (res) => {
						console.log("通过 accountId 删除数据成功",res)
						// 删除数据后跳转会 账户信息 页
						uni.reLaunch({
							url: '../Message'
						})
					}
				})
			},
			gridChange(index){
				console.log("点击改变",index.detail);
			},
			toHome(){
				uni.reLaunch({
					url: '../../home/home'
				})
			},
			toAdd(){
				uni.reLaunch({
					url: '../../Add/Add'
				})
			},
			toMessage(){
				uni.reLaunch({
					url: '../Message'
				})
			},
			toTransfer(){
				uni.reLaunch({
					url: '../transfer/transfer?accountId='+this.accountId+'&freeMoney='+this.workingCapital,
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
.loanAmount{
	display: flex;
}
.side{
	display: flex;
	flex-direction: column;
	width: 100rpx;
}
.side>view{
	width: 100rpx;
	height: 200rpx;
	margin-top: 100rpx;
}
.side>view>text{
	width: 30rpx;
	display: block;
	text-align: center;
	line-height: 50rpx;
	padding-left: 25rpx;
}
.bgColor{
	background-color: #0000FF;
}
.content{
	width: 500rpx;
	height: 500rpx;
	margin-top: -600rpx;
	margin-left: 200rpx;
}
.content>view{
	width: 250rpx;
	height: 250rpx;
	line-height: 250rpx;
}
</style>