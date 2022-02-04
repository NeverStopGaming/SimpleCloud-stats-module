<p align="center">
  <img src="https://img.neverstopgaming.net/Chaoten/SimpleCloud-stats-module.png" alt="Logo">
</p>

<p>
  <p align="center">
    A SimpleCloud module for display statistic from your cloud in grafana using influxdb
    <br />
    <a href="https://dsc.gg/NSG">Discord</a>
    ·
    <a href="https://ts3server://thesimplecloud.eu">Teamspeak</a>
  </p>

</p>

<br />
<br />

<details open="open">
  <summary>Overview</summary>
  <ol>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#requirements">Requirements</a></li>
        <li><a href="#install-the-module">Install the module</a></li>
        <li><a href="#install-influxdb">Install InfluxDB</a></li>
        <li><a href="#install-grafana">Install Grafana</a></li>
       <li><a href="#installUsingDockercCompose">Install Granfa and InfluxDB using Docker compose (recommended)</a></li>
      </ul>
    </li>
    <li><a href="#features">Features</a></li>
    <li><a href="#dashboards">Dashboards</a></li>
  </ol>
</details>

<br />

# Getting Started

## Requirements
* **Java 11** or higher
* [Docker](https://docs.docker.com/engine/install/)
* [InfluxDB](https://hub.docker.com/_/influxdb)
* [Grafana](https://hub.docker.com/r/grafana/grafana)
* [(Docker Compose)](https://docs.docker.com/compose/install/)

## Install the module

1. Download the [last version](https://github.com/NeverStopGaming/SimpleCloud-stats-module/releases/download/v1.1/SimpleCloud-Stats-Module-1.0.jar) from the release
2. Drag and drop it in to your cloud directory `/modules`
3. Reload your cloud using `reload`
4. open the `config.json` in your `/modules/SimpleCloud-Stats-Module/` directory and enter your influxdb credentials

## Install InfluxDB

1. run `docker run -p 8086:8086 -v influxdb:/var/lib/influxdb2 influxdb` to pull the influxdb image run start a instance unter the port `8086`
2. navigate to http://yourserverip:8086 and go through the installation
3. create a new Bucket named `Simplecloud` 

## Install Grafana

1. use `docker run -d --name=grafana -p 3000:3000 grafana/grafana` to download the grafana docker image and run it
2. open http://yourserverip:3000 in your browser and login with the default username `admin` und password `admin`
3. onces logged in head to `Configuration` and `Data sources`
4. click on `add data source` and select `InfluxDB`
5. set the `Query Language` to `Flux` and enter your `InfluxDB Details`
6. (Get a api in your `Influx web interface` under `Data` and `API Token`)

<h2 id="installUsingDockercCompose">Install InfluxDB and Grafana using Docker compose (recommended)</h3>

1. __TODO__

# Features
* real time data
* prebuild dashboards
* advanced player, service, group and wrapper statistics
* custom querrys
* custom dashboards

<br />

# Dashboards
__TODO__
