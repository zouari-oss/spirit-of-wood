[![Contributors](https://img.shields.io/badge/CONTRIBUTORS-01-blue?style=plastic)](https://github.com/zouari-oss/spirit-of-wood/graphs/contributors)
[![Forks](https://img.shields.io/badge/FORKS-00-blue?style=plastic)](https://github.com/zouari-oss/spirit-of-wood/network/members)
[![Stargazers](https://img.shields.io/badge/STARS-01-blue?style=plastic)](https://github.com/zouari-oss/spirit-of-wood/stargazers)
[![Issues](https://img.shields.io/badge/ISSUES-00-blue?style=plastic)](https://github.com/zouari-oss/spirit-of-wood/issues)
[![GPL3.0 License](https://img.shields.io/badge/LICENSE-GPL3.0-blue?style=plastic)](https://raw.githubusercontent.com/zouari-oss/spirit-of-wood/refs/heads/main/LICENSE)
[![Linkedin](https://img.shields.io/badge/Linkedin-7k-blue?style=plastic)](https://www.linkedin.com/in/zouari-omar)

<div align="center">
  <a href="https://github.com/zouari-oss/crun">
    <img src="https://github.com/zouari-oss/spirit-of-wood/raw/main/res/img/logo.png" alt="crun-logo" width="300">
  </a>
  <h1>spirit-of-wood</h1>
  <h4>A modern web application built with a Spring Boot backend and Angular frontend</h4>
  <br />
</div>

<p align="center">
  <a href="#overview">Overview</a> •
  <a href="#key-features">Key Features</a> •
  <a href="#usage">Usage</a> •
  <a href="#download">Download</a> •
  <a href="#emailware">Emailware</a> •
  <a href="#contributing">Contributing</a> •
  <a href="#license">License</a> •
  <a href="#contact">Contact</a> •
  <a href="#acknowledgments">Acknowledgments</a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white"/>
  <img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/>
  <img src="https://img.shields.io/badge/bash_script-%23121011.svg?style=for-the-badge&logo=gnu-bash&logoColor=white"/>
  <img src="https://img.shields.io/badge/SSR-Yes-4CAF50?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Cross--Platform-Yes-success?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Open%20Source-3DA639?style=for-the-badge&logo=opensourceinitiative&logoColor=white"/>
</p>

![screenshot](https://github.com/zouari-oss/spirit-of-wood/raw/main/res/img/screenshot.png)

## Overview

Spirit-of-Wood is a modern web application built with a Spring Boot backend and Angular frontend.
It provides a dynamic and responsive interface to showcase wooden products, brand information, and contact details.  
The application supports server-side rendering (SSR), is cross-platform, and leverages configuration-driven properties for scalability.

## Key Features

- **Product Showcase**: Display handcrafted wooden items with images, descriptions, and categories.
- **Responsive Design**: Works seamlessly across desktop, tablet, and mobile devices.
- **Server-Side Rendering (SSR)**: Improves SEO and faster initial page loads.
- **REST API Backend**: Built with Spring Boot, exposing endpoints for frontend consumption.
- **Configuration Management**: Uses Spring Boot `@ConfigurationProperties` for flexible application settings.
- **Cross-Platform Compatibility**: Runs on any JVM-supported environment.
- **Testing Ready**: JUnit 5 and WebTestClient integration for backend testing.
- **Build & Automation**: Supports Gradle and Maven for easy build and deployment.
- **Open Source**: Fully open-source project for learning and contribution.

## Usage

```bash
git clone https://github.com/zouari-oss/spirit-of-wood
cd spirit-of-wood/project
```

### Front End

```bash
cd front-end
npm install
ng serve
```

#### Production build

```bash
ng build --prod
```

> [!IMPORTANT]
> The compiled output will be in `dist/front-end/`
> Can be served by any static server or integrated with the backend

### Back End

```bash
cd back-end
./gradlew build
./gradlew bootRun
```

> [!IMPORTANT]
> Runs the backend API at <http://localhost:8080>
> Make sure your frontend points to the correct API URL if testing locally

## Download

You can [download](https://github.com/zouari-oss/spirit-of-wood/releases) the latest installable version of spirit-of-wood for Windows, macOS and Linux.

## Emailware

spirit-of-wood is an emailware. Meaning, if you liked using this app or it has helped you in any way,
would like you send as an email at <zouariomar20@gmail.com> about anything you'd want to say about
this software. I'd really appreciate it!

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This repository is licensed under the **GPL-3.0 License**. You are free to use, modify, and distribute the content. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or suggestions, feel free to reach out:

- **GitHub**: [zouari-oss](https://github.com/zouari-oss)
- **Email**: <zouariomar20@gmail.com>
- **LinkedIn**: [zouari-omar](https://www.linkedin.com/in/zouari-omar)

## Acknowledgments

Built with ❤️ for the open-source community.
