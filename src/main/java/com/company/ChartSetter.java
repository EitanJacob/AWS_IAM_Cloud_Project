package com.company;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ChartSetter {

    ServiceStatistic serviceStatistic;
    final String RESOURCE_CHART_NAME= "Actions With Resources";
    final String ACTION_TYPE_CHART_NAME= "Most Common Action Prefixes";
    final String CONDITION_CHART_NAME= "Actions With Condition Keys";
    final String DEPENDENT_ACTION_CHART_NAME= "Actions With Dependent Actions";
    final String ACCESS_LEVEL_CHART_NAME= "Actions With Associated Access Levels";

    public ChartSetter(ServiceStatistic serviceStatistic) {
        this.serviceStatistic = serviceStatistic;
    }

    public void makeResourceChart() {
        ChartMaker chartMaker = new ChartMaker(RESOURCE_CHART_NAME, 2,
                ResourceStatus.NoResources.toString(),
                ResourceStatus.ContainsResources.toString(),
                serviceStatistic.getDoesNotSupportResource(),
                serviceStatistic.supportsResource);
        chartMaker.makeChart();
    }

    public void makeDependentActionChart() {
        ChartMaker chartMaker = new ChartMaker(DEPENDENT_ACTION_CHART_NAME, 2,
                DependentStatus.NoDependentActions.toString(),
                DependentStatus.ContainsDependActions.toString(),
                serviceStatistic.noDependentActions,
                serviceStatistic.containsDependantActions);
        chartMaker.makeChart();
    }

    public void makeConditionChart() {
        ChartMaker chartMaker = new ChartMaker(CONDITION_CHART_NAME, 3,
                ConditionStatus.NoConditions.toString(),
                ConditionStatus.ContainsOnlyNonSpecific.toString(),
                ConditionStatus.ContainsSpecific.toString(),
                serviceStatistic.noConditionKeys,
                serviceStatistic.ContainsOnlyNonSpecificKeys,
                serviceStatistic.ContainsSpecificKeys);
        chartMaker.makeChart();
    }

    public void makeAccessLevelChart() {
        ChartMaker chartMaker = new ChartMaker(ACCESS_LEVEL_CHART_NAME, 5,
                AccessLevel.Permissions_management.toString(),
                AccessLevel.Read.toString(),
                AccessLevel.List.toString(),
                AccessLevel.Tagging.toString(),
                AccessLevel.Write.toString(),
                serviceStatistic.permissionsManagementActions,
                serviceStatistic.readActions,
                serviceStatistic.listAccessActions,
                serviceStatistic.tagActions,
                serviceStatistic.writeActions);
        chartMaker.makeChart();
    }

    public void makeActionTypeChart() {

        ChartMaker chartMaker = new ChartMaker(ACTION_TYPE_CHART_NAME, 11,
                ActionType.Create.toString(),
                ActionType.Delete.toString(),
                ActionType.Tag.toString(),
                ActionType.Put.toString(),
                ActionType.Start.toString(),
                ActionType.Get.toString(),
                ActionType.Other.toString(),
                ActionType.Describe.toString(),
                ActionType.Untag.toString(),
                ActionType.Update.toString(),
                ActionType.List.toString(),
                serviceStatistic.createActions,
                serviceStatistic.deleteActions,
                serviceStatistic.tagActions,
                serviceStatistic.putActions,
                serviceStatistic.startActions,
                serviceStatistic.getActions,
                serviceStatistic.otherActions,
                serviceStatistic.describeActions,
                serviceStatistic.untagActions,
                serviceStatistic.updateActions,
                serviceStatistic.listPrefixActions);
        chartMaker.makeChart();
    }

    private class ChartMaker {

        String chartName;
        int params;
        String KEY1, KEY2, KEY3, KEY4, KEY5, KEY6, KEY7, KEY8, KEY9, KEY10, KEY11;
        int num1, num2, num3, num4, num5, num6, num7, num8, num9, num10, num11;

        public ChartMaker(String chartName, int params, String KEY1, String KEY2, int num1, int num2) {
            this.chartName = chartName;
            this.params = params;
            this.KEY1 = KEY1;
            this.KEY2 = KEY2;
            this.num1 = num1;
            this.num2 = num2;
        }

        public ChartMaker(String chartName, int params, String KEY1, String KEY2, String KEY3, int num1, int num2, int num3) {
            this.chartName = chartName;
            this.params = params;
            this.KEY1 = KEY1;
            this.KEY2 = KEY2;
            this.KEY3 = KEY3;
            this.num1 = num1;
            this.num2 = num2;
            this.num3 = num3;
        }

        public ChartMaker(String chartName, int params, String KEY1, String KEY2, String KEY3, String KEY4, String KEY5,
                          int num1,
                          int num2, int num3,
                          int num4, int num5) {
            this.chartName = chartName;
            this.params = params;
            this.KEY1 = KEY1;
            this.KEY2 = KEY2;
            this.KEY3 = KEY3;
            this.KEY4 = KEY4;
            this.KEY5 = KEY5;
            this.num1 = num1;
            this.num2 = num2;
            this.num3 = num3;
            this.num4 = num4;
            this.num5 = num5;
        }

        public ChartMaker(String chartName, int params, String KEY1, String KEY2, String KEY3, String KEY4,
                          String KEY5,
                          String KEY6, String KEY7,
                          String KEY8, String KEY9, String KEY10, String KEY11, int num1, int num2, int num3,
                          int num4, int num5, int num6, int num7, int num8, int num9, int num10, int num11) {
            this.chartName = chartName;
            this.params = params;
            this.KEY1 = KEY1;
            this.KEY2 = KEY2;
            this.KEY3 = KEY3;
            this.KEY4 = KEY4;
            this.KEY5 = KEY5;
            this.KEY6 = KEY6;
            this.KEY7 = KEY7;
            this.KEY8 = KEY8;
            this.KEY9 = KEY9;
            this.KEY10 = KEY10;
            this.KEY11 = KEY11;
            this.num1 = num1;
            this.num2 = num2;
            this.num3 = num3;
            this.num4 = num4;
            this.num5 = num5;
            this.num6 = num6;
            this.num7 = num7;
            this.num8 = num8;
            this.num9 = num9;
            this.num10 = num10;
            this.num11 = num11;
        }

        public void makeChart() {

            DefaultPieDataset dataset = new DefaultPieDataset();

            dataset.setValue(KEY1, num1);
            dataset.setValue(KEY2, num2);
            if (!(KEY3 == null)) {
                dataset.setValue(KEY3, num3);
                if (!(KEY4 == null)) {
                    dataset.setValue(KEY4, num4);
                    dataset.setValue(KEY5, num5);
                    if (!(KEY6 == null)) {
                        dataset.setValue(KEY6, num6);
                        dataset.setValue(KEY7, num7);
                        dataset.setValue(KEY8, num8);
                        dataset.setValue(KEY9, num9);
                        dataset.setValue(KEY10, num10);
                        dataset.setValue(KEY11, num11);
                    }
                }
            }

            JFreeChart someChart = ChartFactory.createPieChart(
                    chartName, dataset, true, true, false);

            PiePlot plot = (PiePlot) someChart.getPlot();
            plot.setSectionPaint(KEY1, Color.green);
            plot.setSectionPaint(KEY2, Color.red);
            if (!(KEY3 == null)) {
                plot.setSectionPaint(KEY3, Color.yellow);
                if (!(KEY4 == null)) {
                    plot.setSectionPaint(KEY4, Color.black);
                    plot.setSectionPaint(KEY5, Color.blue);
                    if (!(KEY6 == null)) {
                        plot.setSectionPaint(KEY6, Color.darkGray);
                        plot.setSectionPaint(KEY7, Color.lightGray);
                        plot.setSectionPaint(KEY8, Color.magenta);
                        plot.setSectionPaint(KEY9, Color.cyan);
                        plot.setSectionPaint(KEY10, Color.pink);
                        plot.setSectionPaint(KEY11, Color.white);
                    }
                }
            }
                //plot.setExplodePercent(KEY1, 0.10);
                plot.setSimpleLabels(true);

                PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                        "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
                plot.setLabelGenerator(gen);

                JFrame f = new JFrame("Test");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.add(new ChartPanel(someChart) {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(400, 300);
                    }
                });
                f.pack();

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
            Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();

            int x;
            int y;

            switch (chartName) {
                case CONDITION_CHART_NAME:
                    x = (int) rect.getMaxX() - f.getWidth();
                    y = 0;
                    f.setLocation(x, y);
                    break;
            }
            switch (chartName) {
                case DEPENDENT_ACTION_CHART_NAME:
                    x = 0;
                    y = (int) rect.getMaxY()- f.getHeight();
                    f.setLocation(x, y);
                    break;
            }
            switch (chartName) {
                case RESOURCE_CHART_NAME:
                    x = 0;
                    y = 0;
                    f.setLocation(x, y);
                    break;
            }
            switch (chartName) {
                case ACCESS_LEVEL_CHART_NAME:
                    x = (int) rect.getMaxX() - f.getWidth();
                    y = (int) rect.getMaxY()- f.getHeight();
                    f.setLocation(x, y);
                    break;
            }
            switch (chartName) {
                case ACTION_TYPE_CHART_NAME:
                    f.setLocationRelativeTo(null);
                    break;
            }
                f.setVisible(true);
            }
        }
    }