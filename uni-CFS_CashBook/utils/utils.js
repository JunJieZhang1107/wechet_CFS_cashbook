export default {
  utcToBeijing(utc_datetime) { // utc转北京时间  例： 2020-05-08T04:25:44.000Z  -> 2020/05/08 12:25
    let T_pos = utc_datetime.indexOf("T");
    let Z_pos = utc_datetime.indexOf("Z");
    let year_month_day = utc_datetime.substr(0, T_pos);
    let hour_minute_second = utc_datetime.substr(T_pos + 1, Z_pos - T_pos - 1);
    let new_datetime = (year_month_day + " " + hour_minute_second).replace(/\.000/g, ""); // 去掉结尾.000  .replace(/\.|0+$/g, "")
    let a = new Date(Date.parse(new_datetime.replace(/\-/g, "/"))); // - ios不支持，所以替换为 / 
    let b = (a.getTime()) / 1000 + 8 * 60 * 60;
    let data = new Date(parseInt(b) * 1000);
    let beijing_datetime = this.addZero(data.getFullYear()) + "/" + (this.addZero(data.getMonth() + 1)) + "/" + this.addZero(data.getDate()) + " " + this.addZero(data.getHours()) + ":" + this.addZero(data.getMinutes());
    return beijing_datetime;
  },
  addZero(node) { // 个位不足10补0
    return node < 10 ? "0" + node : node;
  }
}
