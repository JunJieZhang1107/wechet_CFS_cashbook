<template>
	<view>
		<view class="status_bar"></view>
		<nav-bar @clickhome="toHome()" @clickadd="toAdd()" @clickmessage="toMessage"></nav-bar>
		<view class="details">
			<view class="side">
				<view @click="toDetails">
					<text>详情信息</text>
				</view>
				<view class="bgColor">
					<text>资金账户转移</text>
				</view>
			</view>
		</view>
		<view class="content">
			<uni-forms>
				<uni-forms-item label="账户名:" name="">
					<picker 
					mode="selector" 
					:range="selectAccount" 
					:value="accountIndex" 
					@change="pickerSelect"
					class="pickerView">
						<view>{{ selectAccount[accountIndex] }}</view>
					</picker>
				</uni-forms-item>
				<uni-forms-item label="金额:" name="">
					<input type="number" v-model="money" value="" />
				</uni-forms-item>
				<button @click="submit">提交</button>
			</uni-forms>
		</view>
		<tab-bar :current="0"></tab-bar>
	</view>
</template>

<script>
	export default {
		onLoad: function (option) {
		        this.accountId = parseInt(option.accountId);
				this.freeMoney = parseFloat(option.freeMoney);
		    },
		data() {
			return {
				selectAccount: [], // 名字
				accountIndex: 0, // 对应的下标
				accountIdList:[], // 对应的账号id
				money: 0,
				accountId:0,
				freeMoney:0,
				userId:0,
			}
		},
		methods: {
			// 轉賬操作
			submit(){
				if(this.freeMoney >= this.money){
					// 提交更新的数据，当前账号id, 转账金额, 转账的账户
					// TODO: 转账账户寫好了。
					uni.request({
						url: "http://localhost:8080/exchangeMoney/"+ this.accountId+"/"+this.accountIdList[this.accountIndex],
						method: 'GET',
						data:{
							money: this.money
						},
						success: (res) => {
							if(res.data.msg == "转账成功")
								uni.showToast({
									title: res.data.msg,
								})
						},
						fail: (err) => {
							console.log("获取数据失败",err)
							uni.showToast({
								title: '提交失败',
								icon: 'none'
							})
						}
					})
					
				}else{
					uni.showToast({
						title: '金额不足，不允提交',
						icon:'none'
					})
				}
			},
			pickerSelect(e){
				this.accountIndex = e.target.value
				
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
			toDetails(){
				uni.reLaunch({
					url: '../details/details?accountId='+this.accountId,
				})
			}
		},
		mounted(){
				//  获取用户账号信息
			uni.getStorage({
				key: 'userId',
				success: (res) => {
					this.userId = res.data
					uni.request({
						url: "http://localhost:8080/filterAccount/"+this.userId+"/"+this.accountId,
						method: 'GET',
						success: (res) => {
							this.selectAccount = [];
							this.accountIdList= [];
							res.data.allAccount.forEach((value,index,array) =>{
								var accountId = value.accountId;
								var accountName = value.accountName;
								this.selectAccount.push(accountName);
								this.accountIdList.push(accountId);
							})
						},
						fail: (err) => {
							console.log("获取数据失败",err)
							
						}
					})
				}
			})
			},
		
			
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
	height: 100%;
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
	height: 100%;
	margin-top: -650rpx;
	margin-left: 200rpx;
	display: flex;
	flex-wrap: wrap;
}
.content>view{
	width: 250rpx;
	height: 250rpx;
	line-height: 250rpx;
}
.pickerView{
	margin-top: 20rpx;
}
input{
	display: block;
	margin-top: 20rpx;
}
button{
	width: 300rpx;
}
</style>
