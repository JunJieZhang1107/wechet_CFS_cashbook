<template>
	<view class="list">
		<view class="status_bar"></view>
		<inan-nav-bar :current="4"></inan-nav-bar>
		<scroll-view scroll-y="true" :scroll-top="scrollTop" class="scrollY">
			<view class="content">
				<view v-for="(item,index) in data" style="padding-top: 10px;">
					<view class="up">
						<text >{{item.accountName}}</text>
						<text class="right">{{item.time}}</text>
					</view>
					<view class="in">
						<text>金额：{{  item.money }}</text>
						<text class="right" v-text="item.type? '收入' : '支出'"></text>
					</view>
					<view class="down">
						<text>原因：{{ item.reason }}</text>
					</view>
				</view>
			</view>
		</scroll-view>
		<tab-bar :current="1" ></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				data:[]
			}
		},
		methods: {
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
			getData(){
				uni.getStorage({
					key: 'userId',
					success: (res) => {
						this.userId = res.data
						// 获取用户账号数据
						uni.request({
							url: 'http://localhost:8080/record',
							method: 'GET',
							data:{
								userId : res.data
							},
							success: (res) => {
								this.data = [];
								res.data.forEach((item,index,arr)=>{
									var tmp = {};
									tmp = item;
									tmp.time = this.utcToBeijing(item.time)
									this.data.push(tmp);
								})
							}
						})
					}
				});
			}
		},
		created(){
			this.getData();
		}
	}
</script>

<style>
.content{
	width: 100%;
	height: 1000rpx;
	display: flex;
	flex-direction: column;
	margin-bottom: 130rpx;
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
.content{
	margin-left: 20rpx;
}
</style>
