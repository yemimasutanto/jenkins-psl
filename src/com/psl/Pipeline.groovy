package com.psl

import com.psl.Config

class Pipeline {
    String repository_name
    String branch_name
    String git_user
    String app_port
    String pr_num
    String dockerTool

    Pipeline(
        String repository_name,
        String branch_name,
        String git_user,
        String app_port,
        String pr_num,
        String dockerTool
    ) {
        this.repository_name = repository_name
        this.branch_name = branch_name
        this.git_user = git_user
        this.app_port = app_port
        this.pr_num = pr_num
        this.dockerTool = dockerTool
    }
}