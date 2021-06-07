<template>
	<view>
		<view class="status_bar"></view>
		<remind-nav-bar :current="1"></remind-nav-bar>
		<scroll-view scroll-y="true" :scroll-top="scrollTop" class="scrollY"
		>
		<view v-for="(item,index) in list">
			<min-slip
			theme="#fff" 
			btntxt="还款" 
			btntxtcolor="#000" 
			btncolor="red"
			isHeight="150"
			class="minSlip"
			@click="deleteItem(item)"
			:key="index">
				<view class="content">
					<view class="up">
						<text>账户：{{ item.accountName }}</text>
					</view>
					<view class="in">
						<text>负债金额：{{ item.debtPay }}</text>
					</view>
					<view class="down">
						<text v-if="item.payTime >= 0">剩余： {{ item.payTime}} 天</text>
						<text v-else>已预期 </text>
					</view>
					
				</view>
			</min-slip>
			</view>
		</scroll-view>
		<tab-bar :current="3"></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list:[],
			}
		},
		methods: {
			// 获取负债信息（日期转换)
			getData(){
				this.list =[];
				uni.getStorage({
					key: 'userId',
					success: (res) => {
						this.userId = res.data
						// 获取用户账号数据
						uni.request({
							url: 'http://localhost:8080/debt',
							method: 'GET',
							data: {
								userId: res.data
							},
							success: (res) => {
								this.list = res.data;
								console.log(res.data);
							}
						})
					}
				});
			},
			// 还款
			deleteItem(item){
				console.log(item);
				uni.request({
					url: 'http://localhost:8080/debt',
					method: 'PUT',
					data: {
						id:item.id,
						accountId:item.accountId,
						debtPay:item.debtPay
					},
					success: (res) => {
						if(res.data.msg == "删除成功"){
							this.getData();
							uni.showToast({
								title:res.data.msg,
							})
						}else{
							uni.showToast({
								title:res.data.msg,
								icon: "none"
							})
						}
					}
				})
			},
			utcToBeijing(utc_datetime) { // utc转北京时间  例： 2020-05-08T04:25:44.000Z  -> 2020/05/08 12:25
				let T_pos = utc_datetime.indexOf("T");
				let Z_pos = utc_datetime.indexOf("Z");
				let year_month_day = utc_datetime.substr(0, T_pos);
				let hour_minute_second = utc_datetime.substr(T_pos + 1, Z_pos - T_pos - 1);
				let new_datetime = (year_month_day + " " + hour_minute_second).replace(/\.000/g, ""); // 去掉结尾.000  .replace(/\.|0+$/g, "")
				let a = new Date(Date.parse(new_datetime.replace(/\-/g, "/"))); // - ios不支持，所以替换为 / 
				let b = (a.getTime()) / 1000 + 8 * 60 * 60;
				let data = new Date(parseInt(b) * 1000);
				let beijing_datetime = this.addZero(data.getFullYear()) + "/" + (this.addZero(data.getMonth() + 1)) + "/" + this.addZero(data.getDate());
				return beijing_datetime;
			},
			addZero(node) { // 个位不足10补0
				return node < 10 ? "0" + node : node;
			},
		},
		created(){
			this.getData();
		}
	}
</script>

<style>
.lists{
	width: 100%;
	margin-top: 20rpx;
}
.content{
	width: 100%;
	display: flex;
	flex-direction: column;
}
.up{
	width: 100%;
	flex-direction: row;
}
.right{
	position: absolute;
	right: 0;
	color: #999999;
}
.scrollY{
	height: 1000rpx;
}
</style>
