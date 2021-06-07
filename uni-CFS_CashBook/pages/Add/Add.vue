<template>
	<view>
		<view class="status_bar"></view>
		<nav-bar @clickhome="toHome()" @clickmessage="toMessage()"></nav-bar>
		<view  style="margin-left: 15%;width: 70%;margin-top:10%">
			<uni-forms ref="form" >
				<uni-forms-item label="账户名" name="">
					<input type="text" value="" v-model="accountName" style="margin-top: 20rpx;"/>
				</uni-forms-item>
				<uni-forms-item label="账户类型" name="">
					<input type="text" value="" style="margin-top: 20rpx;"/>
				</uni-forms-item>
				<uni-forms-item label="流动资金" name="">
					<input type="text" value="" v-model="freeMoney" style="margin-top: 20rpx;"/>
				</uni-forms-item>
				<button @click="postAccount">提交</button>
			</uni-forms>
		</view>
		<tab-bar></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				accountName: '', // 账户名
				freeMoney: '',	// 流动资金
				userId: ''	// 用户唯一值
			}
		},
		mounted(){
			// 获取本地缓存中的userId
			uni.getStorage({
				key: 'userId',
				success: (res) => {
					console.log("获取缓存数据成功",res.data)
					this.userId = res.data
				}
			})
		},
		methods: {
			// 添加数据
			postAccount(){
				// 流动资金只能为0-9的数字
				let ckeckMoney = RegExp(/^[0-9]/)
				// 判断流动资金输入的格式是否正确
				if (ckeckMoney.test(this.freeMoney)){
					// 账户名和流动资金都不为空的情况下
					if (this.accountName != '' && this.freeMoney != ''){
						// 添加资金账户信息: 账户名、流动资金、用户唯一值
						uni.request({
							url: 'http://localhost:8080/account',
							method: 'POST',
							data:{
								accountName: this.accountName,
								accountFreeMoney: this.freeMoney,
								userId: this.userId
							},
							success: (res) => {
								if (res.statusCode == 200){
									if(res.data.msg == "该资金账户已存在")
									{
										uni.showToast({
											title: '该资金账户已存在',
											icon:'none'
										})
									}	
									console.log("添加资金信息成功",res)
								}
							},
							fail: (err) => {
								console.log("添加资金信息失败",err)
							}
						})	
						// 如果账户名为空
					}else if (this.accountName == ''){
						// 弹窗提示
						uni.showToast({
							title: '账户名不能为空',
							icon: 'none'
						})
						// 如果流动资金为空
					}else if (this.freeMoney == ''){
						// 弹窗提示
						uni.showToast({
							title: '资金不能为空'
						})
					}
				}else{
					// 如果资金不是0-9的数字的话，弹窗提示
					uni.showToast({
						title: '资金只能是数字',
						icon:'none'
					})
				}
			},
			// 页面跳转到 首页
			toHome(){
				uni.reLaunch({
					url:"../home/home"
				})
			},
			// 页面跳转到 账户信息
			toMessage(){
				uni.reLaunch({
					url: "../Message/Message"
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
}
.navBarRight{
	width: 250rpx;
	text-align: center;
	font-size: 30rpx;
	background-color: #0000FF;
}
input{
	border: 1px solid;
}
</style>
