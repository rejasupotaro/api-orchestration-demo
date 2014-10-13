require File.expand_path('../boot', __FILE__)

require 'rails/all'

# Require the gems listed in Gemfile, including any gems
# you've limited to :test, :development, or :production.
Bundler.require(*Rails.groups)

module Rest
  class Application < Rails::Application
    config.after_initialize do
      Rails.application.reload_routes!
      all_routes = Rails.application.routes.routes
      require 'action_dispatch/routing/inspector'
      inspector = ActionDispatch::Routing::RoutesInspector.new(all_routes)
      puts inspector.format(ActionDispatch::Routing::ConsoleFormatter.new, ENV['CONTROLLER'])
    end
  end
end
