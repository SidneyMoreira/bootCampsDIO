build-image:
	docker build -t sidmoreira/finance .

run-app:
	docker-compose -f .devops/app.yml up -d

lint:
	golint ./...
	go fmt -m ./...

unit_test:
	go test ./...