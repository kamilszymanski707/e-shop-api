import json
import os
import sys
from distutils.dir_util import copy_tree

from git import Repo

cloud_core_dir = "cloud-core"
cloud_core_git_uri = "https://github.com/kamilszymanski707/cloud-core.git"
back = ".."
app_dir = "e-shop"
e_shop_api_dir = "e-shop-api"
e_shop_api_tmp_dir = "docker/tmp"
keycloak_json_file_name = "realm-export.json"

os.chdir(back)

app_path = os.path.join(os.curdir, app_dir)

os.mkdir(app_path)

Repo.clone_from(cloud_core_git_uri, app_dir + "/" + cloud_core_dir)

copy_tree(e_shop_api_dir, app_dir + "/" + e_shop_api_dir)

os.chdir(app_dir)

os.chdir(e_shop_api_dir)

os.chdir(e_shop_api_tmp_dir)

with open(keycloak_json_file_name, "r") as jsonFile:
    data = json.load(jsonFile)

attributes_json = data["attributes"]

if sys.argv[1] != "docker":
    frontendUrl = "http://localhost:8080/auth"
else:
    frontendUrl = "http://keycloak:8080/auth"

attributes_json["frontendUrl"] = frontendUrl

with open(keycloak_json_file_name, "w") as jsonFile:
    json.dump(data, jsonFile)

os.chdir(back)
os.chdir(back)

os.chdir("scripts")

os.system("sh mvn-build.sh")

os.chdir(back)

if sys.argv[1] == "docker":
    os.chdir("docker")
    os.system("docker-compose up -d")
    os.chdir(back)

os.chdir(back)
os.chdir(back)

os.system("rm -rf " + e_shop_api_dir)
