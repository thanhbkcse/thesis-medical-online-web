<template>
  <div class="content">
    <v-row>
      <v-col cols="12">
        <v-row>
          <v-col
            v-for="(chart, i) in charts"
            :key="`chart-${i}`"
            cols="12"
            md="6"
            lg="4"
          >
            <material-chart-card
              :color="chart.color"
              :data="chart.data"
              :options="chart.options"
              :responsive-options="chart.responsiveOptions"
              :title="chart.title"
              :type="chart.type"
            >
              <template #subtitle>
                <div class="font-weight-light text--secondary">
                  <div v-html="chart.subtitle" />
                </div>
              </template>

              <template #actions>
                <v-icon class="mr-1" small>
                  mdi-clock-outline
                </v-icon>

                <span
                  class="text-caption grey--text font-weight-light"
                  v-html="chart.time"
                />
              </template>
            </material-chart-card>
          </v-col>
        </v-row>
      </v-col>

      <v-col
        v-for="({ actionIcon, actionText, ...attrs }, i) in numberOfs"
        :key="i"
        cols="12"
        md="6"
        lg="3"
      >
        <material-stat-card v-bind="attrs">
          <template #actions>
            <v-icon class="mr-2" small v-text="actionIcon" />
            <div class="text-truncate ">
              {{ actionText }}
            </div> 
          </template>
        </material-stat-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
// Utilities
import { get } from "vuex-pathify";
import Vue from "vue";

const lineSmooth = Vue.chartist.Interpolation.cardinal({
  tension: 0
});

export default {
  data: () => ({
    charts: [
      {
        type: "Bar",
        color: "success",
        title: "Số lượng bệnh nhân theo tháng",
        subtitle: "Biểu đồ mô tả số lượng bệnh nhân trong năm",
        time: "Cập nhật mới nhất trong ngày",
        data: {
          labels: [
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12"
          ],
          series: [[107, 135, 187, 190, 30, 0, 0, 0, 0, 0, 0, 0]]
        },
        options: {
          axisX: {
            showGrid: true
          },
          low: 0,
          high: 200,
          chartPadding: {
            top: 0,
            right: 5,
            bottom: 0,
            left: 0
          }
        },
        responsiveOptions: [
          [
            "screen and (max-width: 640px)",
            {
              seriesBarDistance: 5,
              axisX: {
                labelInterpolationFnc: function(value) {
                  return value[0];
                }
              }
            }
          ]
        ]
      },
      {
        type: "Bar",
        color: "error",
        title: "Số lượng bệnh nhân trong tuần",
        subtitle:
          "Biểu đồ mô tả số lượng bệnh nhân đặt lịch khám online trong tuần",
        time: "Cập nhật mới nhất trong ngày",
        data: {
          labels: ["Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "CN"],
          series: [[25, 28, 16, 33, 38, 30, 20]]
        },
        options: {
          lineSmooth,
          low: 0,
          high: 40, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
          chartPadding: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
          }
        }
      },
      {
        type: "Line",
        color: "info",
        title: "Số lượng cuộc hẹn trong ngày",
        subtitle: "Biểu đồ mô tả số lượng cuộc hẹn theo từng khoản thời gian",
        time: "Cập nhật mới nhất trong ngày",
        data: {
          labels: [
            "8:00",
            "9:00",
            "10:00",
            "11:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00"
          ],
          series: [[5, 5, 5, 5, 5, 4, 5, 4]]
        },
        options: {
          lineSmooth,
          low: 0,
          high: 10, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
          chartPadding: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
          }
        }
      }
    ],
    numberOfs: [
      {
        actionIcon: "mdi-heart-outline",
        actionText: "Số lượng bác sĩ hiện tại",
        color: "success",
        icon: "mdi-heart-outline",
        title: "Bác sĩ",
        value: "23"
      },
      {
        actionIcon: "mdi-calendar-text",
        actionText: "Số lượng hồ sơ bệnh nhân",
        color: "success",
        icon: "mdi-calendar-text",
        title: "Hồ sơ",
        value: "753"
      },
      {
        actionIcon: "mdi-comment-account-outline",
        actionText: "Số lượng phòng được tạo",
        color: "success",
        icon: "mdi-comment-account-outline",
        title: "Phòng",
        value: "34"
      },
      {
        actionIcon: "mdi-school",
        actionText: "Số lượng chuyên khoa hiện có",
        color: "success",
        icon: "mdi-school",
        title: "Chuyên khoa",
        value: "13"
      },
       {
        actionIcon: "mdi-alarm",
        actionText: "Số lượng cuộc hẹn đang chờ",
        color: "error",
        icon: "mdi-alarm",
        title: "Đang chờ",
        value: "47"
      },
      {
        actionIcon: "mdi-account-multiple",
        actionText: "Số lượng cuộc hẹn đang khám",
        color: "error",
        icon: "mdi-account-multiple",
        title: "Đang khám",
        value: "23"
      },
       {
        actionIcon: "mdi-checkbox-marked-circle",
        actionText: "Số lượng cuộc hẹn hoàn tất",
        color: "error",
        icon: "mdi-checkbox-marked-circle",
        title: "Hoàn tất",
        value: "34"
      },
       {
        actionIcon: "mdi-alert",
        actionText: "Số lượng cuộc hẹn bị hủy",
        color: "error",
        icon: "mdi-alert",
        title: "Bị hủy",
        value: "0"
      }
    ]
  })
};
</script>
