<template>
	<view>
		<view class="status_bar"></view>
		<view class="login">
			<text>登录注册界面</text>
		</view>
		<view class="login-form">
			<uni-forms>
				<uni-forms-item label="邮箱:" name="">
					<input type="text" v-model="user.email" placeholder="请输入邮箱"/>
					<text
					v-if="emailTrue"
					style="color: #999999;"
					>请输入正确的邮箱格式:xxxx@xx.com</text>
				</uni-forms-item>
				<uni-forms-item label="密码:" name="">
					<input type="password" v-model="user.password" placeholder="请输入密码"/>
					<text
					v-if="passwordTrue"
					style="color: #999999;"
					>密码必须由6-16个英文字母和数字的字符串组成</text>
				</uni-forms-item>
				<button
				type="submit" 
				class="submit" 
				open-type="getUserInfo" 
				@getuserinfo="tohome()"
				>登录</button>
				<button @click="postuser" class="submit">注册</button>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userInfo: '', // 用户信息
				nickName: '', // 用户微信昵称
				avatarUrl: '', // 用户微信头像
				userId: '',
				user:{
					email: "", // 用户登录或注册的邮箱
					password: "" ,// 用户登录或注册的密码
				},
				emailTrue: '',
				passwordTrue: ''
			}
		},
		created(){
			
		},
		methods: {
			postuser(){
				// 发送账号密码，注册请求
				// 匹配字母、数字、下划线开头中间有@符号匹配字母、数字、下划线结尾
				let checkEmail = RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)
				// 密码必须由6-16个英文字母和数字的字符串组成
				let checkPassword = RegExp(/^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$/)
				// 判断邮箱格式是否正确
				if(!checkEmail.test(this.user.email)){
					uni.showToast({
						title: "邮箱格式错误",
						icon: 'none'
					})
					// 提示信息
					this.emailTrue = true
					// 判断密码格式是否正确
				}else if(!checkPassword.test(this.user.password)){
					uni.showToast({
						title: '密码格式错误',
						icon: 'none'
					})
					this.emailTrue = false
					// 提示信息
					this.passwordTrue = true
				}else{
					// 邮箱和密码格式都正确后执行
					uni.request({
						url: "http://localhost:8080/user",
						method:"POST",
						data:{
							email: this.user.email,
							password: this.user.password
						},
						success: (res) => {
							console.log(res);
							var title = res.data.msg;
							var icon = 'none';
							if(title == "注册成功,返回用户数据"){
								title = "注册成功";
								icon = "success";
							}
							uni.showToast({
								title:title,
								icon: icon
							})
						}
					})
				}
			},
			tohome(){
				// 请求账号密码，登录验证
				uni.request({
					url: 'http://localhost:8080/user',
					method: 'GET',
					data:{
						email: this.user.email,
						password: this.user.password
					},
					// 请求成功后的操作
					success: (res) => {
						// console.log(res)
						// 状态码200后执行的操作
						if (res.statusCode === 200){
							// 获取服务器响应的数据
							var title = "登录成功";
							var icon = "success";
							if(title != res.data.msg){
								title = "登录失败";
								icon =  "none";
							}
							uni.showToast({
								title:title,
								icon:icon
							})
							// JSON 数据转换
							let data = JSON.parse(res.data.data)
							// console.log(data)
							// 获取 userId (用户唯一值)
							this.userId = data.userId
							// 把 userId 缓存到本地
							uni.setStorage({
								key: 'userId',
								data: this.userId,
								success: (res) => {
									console.log("缓存成功",res)
								}
							})
							// 登录
							uni.login({
								success: (res) => {
									// 登录成功后获取用户信息 uni.getUserInfo
									uni.getUserInfo({
										success: (res) => {
											console.log(res)
											// 把用户信息从 JSON 数据转换成对象
											this.userInfo = JSON.parse(res.rawData)
											// nickName 获取用户名
											this.nickName = this.userInfo.nickName
											// avatarUrl 获取用户微信头像
											this.avatarUrl = this.userInfo.avatarUrl
											// 把用户名和微信头像保存到本地缓存中
											uni.setStorage({
												key: 'nickName',
												data: {
													nickName: this.nickName,
													avatarUrl: this.avatarUrl
												},
												success: (res) => {
													console.log("缓存成功名字和头像成功",res)
												}
											})
											// 跳转页面
											uni.reLaunch({
												url: "../home/home",
												success: (res) => {
													console.log("登录成功")
												},
												fail: (err) => {
													console.log("登录失败")
												}
											})
										}
									})
								}
							})
						}
						
					}
				})
			}
		}
	}
</script>

<style>
.login{
	font-size: 36rpx;
	width: 100%;
	text-align: center;
	font-weight: bold;
	margin-bottom: 30rpx;
}
.login-form{
	width: 80%;
	height: 600rpx;
	border: 1px solid;
	display: flex;
	margin-left: 10%;
}

input{
	border: 1px solid;
	margin-top: 15rpx;
}
uni-forms-item{
	display: block;
	margin-top: 25rpx;
	margin-left: 25rpx;
}

.submit{
	position: relative;
	float: left;
	margin-left: 60rpx;
	width: 200rpx;
	font-size: 12px;
	margin-top: 50rpx;
}
</style>
