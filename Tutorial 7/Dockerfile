FROM alpine
RUN apk update && apk add ruby
WORKDIR /app
ADD . /app
CMD ["ruby", "ruby.rb"]
